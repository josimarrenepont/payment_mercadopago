package com.projeto.mercadopago.order.core.entrypoint.controller;

import com.projeto.mercadopago.order.core.usecase.UpdateOrderStatusUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Test
    @DisplayName("It must successfully process the payment notification from Mercado Pago")
    void shouldProcessPaymentNotification() throws Exception {

        Long orderId = 1L;
        String dataId = "1234567";

        mockMvc.perform(post("/api/v1/orders/{id}/payment-notification", orderId)
                        .param("data_id", dataId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()
                );
        verify(updateOrderStatusUseCase, times(1)).execute(eq(orderId), eq(dataId));
    }

}

