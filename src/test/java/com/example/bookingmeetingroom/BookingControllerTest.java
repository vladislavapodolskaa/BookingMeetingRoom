package com.example.bookingmeetingroom;


import com.example.bookingmeetingroom.controller.BookingController;
import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.service.AuditService;
import com.example.bookingmeetingroom.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private BookingService bookingService;
    @MockitoBean
    private AuditService auditService;


    @Test
    void shouldReturnCreateStatusWhenBookingIsValid() throws Exception {
        String bookingJson = """
                {
                    "userId": 2,
                    "roomId":1,
                    "bookingInterval": {
                    "startTime": "2026-05-23T12:00:00",
                    "endTime": "2026-05-23T13:00:00"
                    },
                    "topicOfMeeting":"Code review"
                }
                """;
        mockMvc.perform(
                        post("/booking")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookingJson))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequestWhenFieldIsEmpty() throws Exception {
        String bookingJson = """
                {
                    "roomId":1,
                    "bookingInterval": {
                    "startTime": "2026-05-23T12:00:00",
                    "endTime": "2026-05-23T13:00:00"
                    },
                    "topicOfMeeting":"Code review"
                }
                """;

        doThrow(new IllegalArgumentException("User id can't be null"))
                .when(bookingService).createBooking(any(Booking.class));

        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isBadRequest());
    }

}
