package hn.com.tigo.simcardinquiry.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsSimcardModel {

	private Long id;

	private LocalDateTime created;

	private Long typeError;

	private String message;

	private Long reference;

	private String srt;

	private String url;
}
