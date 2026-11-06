package ie.atu.sample.dto;

import ie.atu.sample.model.Booking;
import ie.atu.sample.model.BookingStatus;

public record BookingResponse(Long id, String organiserName, int attendeeCount, Long roomId, BookingStatus status) {
    public static BookingResponse from(Booking b) {
        return new BookingResponse(b.getId(), b.getOrganiserName(), b.getAttendeeCount(), b.getRoomId(), b.getStatus());
    }
}
