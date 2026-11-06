package ie.atu.sample.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookingRequest (
    @NotNull(message = "Room ID Is Required")
    Long roomId,

    @NotBlank(message = "Organiser Name Is Required")
    @Size(min = 2, max = 60, message = "Organiser Must Be 2-60 Characters")
    String organiserName,

    @NotNull(message = "Attendee Count Is Required")
    @Positive(message = "Attendee Count Must Be A Positive Whole Number")
    Integer attendeeCount) {}
