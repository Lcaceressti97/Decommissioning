package hn.com.tigo.equipmentaccessoriesbilling.components;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IGenerationReportInvService;

@Component
public class ReportScheduler {

	@Autowired
	private IGenerationReportInvService generationReportInvService;

	@Scheduled(cron = "0 50 23 * * *")
	public void generateReport() {
		generationReportInvService.generateReport();
	}
}