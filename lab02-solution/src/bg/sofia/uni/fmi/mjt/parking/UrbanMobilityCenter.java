package bg.sofia.uni.fmi.mjt.parking;

import bg.sofia.uni.fmi.mjt.parking.location.Location;
import bg.sofia.uni.fmi.mjt.parking.sanction.Sanction;
import bg.sofia.uni.fmi.mjt.parking.sanction.TowAway;
import bg.sofia.uni.fmi.mjt.parking.sanction.WheelClamp;
import bg.sofia.uni.fmi.mjt.parking.vehicle.LicensePlate;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.BlueZone;
import bg.sofia.uni.fmi.mjt.parking.zone.GreenZone;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

import java.time.LocalDateTime;

public class UrbanMobilityCenter implements ParkingClientAPI, ParkingInspectorAPI {
    private static final int MAX_SESSIONS = 1000;

    private final BlueZone blueZone;
    private final GreenZone greenZone;
    private final ParkingSession[] sessions;
    private int sessionsCount;

    public UrbanMobilityCenter(BlueZone blueZone, GreenZone greenZone) {
        if (blueZone == null || greenZone == null) {
            throw new IllegalArgumentException("Zones cannot be null");
        }
        this.blueZone = blueZone;
        this.greenZone = greenZone;
        this.sessions = new ParkingSession[MAX_SESSIONS];
        this.sessionsCount = 0;
    }

    @Override
    public boolean sendSms(String smsNumber, String plate, LocalDateTime moment) {
        if (smsNumber == null || plate == null || moment == null) {
            return false;
        }

        LicensePlate licensePlate = LicensePlate.parse(plate);
        if (licensePlate == null) {
            return false;
        }

        boolean isValidZone = smsNumber.equals(blueZone.getSmsNumber()) ||
                smsNumber.equals(greenZone.getSmsNumber());
        if (!isValidZone) {
            return false;
        }

        String plateStr = licensePlate.toString();
        LocalDateTime expiryTime = moment.plusHours(1);

        if (sessionsCount < MAX_SESSIONS) {
            sessions[sessionsCount++] = new ParkingSession(plateStr, smsNumber, moment, expiryTime);
            return true;
        }

        return false;
    }

    @Override
    public Sanction inspect(Vehicle vehicle, Location location, LocalDateTime moment) {
        if (vehicle == null || location == null || moment == null) {
            return null;
        }

        ParkingZone targetZone = null;
        if (blueZone.contains(location)) {
            targetZone = blueZone;
        } else if (greenZone.contains(location)) {
            targetZone = greenZone;
        }

        if (targetZone == null) {
            return null;
        }

        boolean isPaid = false;
        String vehiclePlate = vehicle.getLicensePlate().toString();

        for (int i = 0; i < sessionsCount; i++) {
            ParkingSession session = sessions[i];
            if (session != null &&
                    session.getPlate().equals(vehiclePlate) &&
                    session.getSmsNumber().equals(targetZone.getSmsNumber())) {

                // Проверяваме дали моментът на инспекция е ВЪТРЕ в интервала [startTime, expiryTime]
                boolean isAfterOrEqualStart = !moment.isBefore(session.getStartTime());
                boolean isBeforeOrEqualExpiry = !moment.isAfter(session.getExpiryTime());

                if (isAfterOrEqualStart && isBeforeOrEqualExpiry) {
                    isPaid = true;
                    break;
                }
            }
        }

        if (isPaid) {
            return null;
        }

        if (targetZone.equals(blueZone)) {
            return new TowAway(vehicle, blueZone);
        } else {
            return new WheelClamp(vehicle, greenZone);
        }
    }
}