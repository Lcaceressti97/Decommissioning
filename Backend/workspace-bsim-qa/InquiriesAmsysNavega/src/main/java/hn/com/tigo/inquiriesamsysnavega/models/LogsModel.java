package hn.com.tigo.inquiriesamsysnavega.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsModel {

	private Long id;

	private LocalDateTime created;

	private Long typeError;

	private String message;

	private Long reference;

	private String srt;

	private String url;
}
