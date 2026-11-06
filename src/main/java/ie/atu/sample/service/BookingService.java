package ie.atu.sample.service;

import feign.FeignException;
import ie.atu.sample.client.RoomClient;
import ie.atu.sample.dto.BookingRequest;
import ie.atu.sample.dto.BookingResponse;
import ie.atu.sample.dto.RoomDto;
import ie.atu.sample.model.Booking;
import ie.atu.sample.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomClient roomClient;

    public BookingService(BookingRepository bookingRepository, RoomClient roomClient) {
        this.bookingRepository = bookingRepository;
        this.roomClient = roomClient;
    }

    public BookingResponse create(BookingRequest bookingRequest) {
        RoomDto room = fetchRoom(bookingRequest.roomId());

        if(bookingRequest.attendeeCount() > room.capacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendee Count " + bookingRequest.attendeeCount() + " Exceeds Capacity " + room.capacity() + " Of " + room.name());
        }

        Booking booking = new Booking();
        booking.setRoomId(bookingRequest.roomId());
        booking.setOrganiserName(bookingRequest.organiserName());
        booking.setAttendeeCount(bookingRequest.attendeeCount());
        return BookingResponse.from(bookingRepository.save(booking));
    }

    public BookingResponse getById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking Not Found: " + id));
        return BookingResponse.from(booking);
    }

    private RoomDto fetchRoom(Long roomId) {
        try{
            return roomClient.getRoom(roomId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room Not Found: " + roomId);
        }
    }
}