package com.example.demo.models.payload.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class MessageResponse {
	private String message;
}
