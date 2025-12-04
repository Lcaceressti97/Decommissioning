package hn.com.tigo.simcardinquiry.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.ConfigParameterEntity;
import hn.com.tigo.simcardinquiry.entities.SimcardOrderControlEntity;
import hn.com.tigo.simcardinquiry.entities.SimcardSuppliersEntity;
import hn.com.tigo.simcardinquiry.entities.SimcardVersionEntity;
import hn.com.tigo.simcardinquiry.models.ConfigParameterModel;
import hn.com.tigo.simcardinquiry.models.GeneralError;
import hn.com.tigo.simcardinquiry.models.GeneralResponse;
import hn.com.tigo.simcardinquiry.models.OrderFilesModel;
import hn.com.tigo.simcardinquiry.models.SimcardOrderControlModel;
import hn.com.tigo.simcardinquiry.repositories.IConfigParameterRepository;
import hn.com.tigo.simcardinquiry.repositories.ISimcardOrderControlRepository;
import hn.com.tigo.simcardinquiry.repositories.ISimcardSuppliersRepository;
import hn.com.tigo.simcardinquiry.repositories.ISimcardVersionRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.IConfigParameterService;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardOrderControlService;
import hn.com.tigo.simcardinquiry.utils.Constants;
import hn.com.tigo.simcardinquiry.utils.FileGenerator;
import hn.com.tigo.simcardinquiry.utils.Util;

@Service
public class SimcardOrderControlServiceImpl implements ISimcardOrderControlService {

	private final ISimcardOrderControlRepository simcardOrderControlRepository;
	private final ISimcardSuppliersRepository simcardSuppliersRepository;
	private final ISimcardVersionRepository simcardVersionRepository;
	private final ILogsSimcardService logsService;
	private final IConfigParameterService configParametersService;
	private final IConfigParameterRepository configParametersRepository;

	public SimcardOrderControlServiceImpl(ISimcardOrderControlRepository simcardOrderControlRepository,
			ISimcardSuppliersRepository simcardSuppliersRepository, ISimcardVersionRepository simcardVersionRepository,
			ILogsSimcardService logsService, IConfigParameterService configParametersService,
			IConfigParameterRepository configParametersRepository) {
		super();
		this.simcardOrderControlRepository = simcardOrderControlRepository;
		this.simcardSuppliersRepository = simcardSuppliersRepository;
		this.simcardVersionRepository = simcardVersionRepository;
		this.logsService = logsService;
		this.configParametersService = configParametersService;
		this.configParametersRepository = configParametersRepository;

	}

	@Override
	public Page<SimcardOrderControlModel> getSimcardOrderControlPaginated(Pageable pageable) {
		// Obtener la página de entidades desde el repositorio
		Page<SimcardOrderControlEntity> entityPage = this.simcardOrderControlRepository.getSimcardOrderControlPaginated(pageable);

		// Convertir las entidades a modelos
		List<SimcardOrderControlModel> models = entityPage.getContent().stream().map(entity -> {
			SimcardOrderControlModel model = entity.entityToModel();

			// Consulta a la tabla de versiones y setea el campo capacidad
			SimcardVersionEntity simcardVersionEntity = this.simcardVersionRepository
					.getCapacityByIdModel(Long.parseLong(model.getModel()));
			if (simcardVersionEntity != null) {
				model.setModel(simcardVersionEntity.getCapacity());
			}

			return model;
		}).collect(Collectors.toList());

		return entityPage.map(SimcardOrderControlEntity::entityToModel);
	}

	@Override
	public List<SimcardOrderControlModel> getOrderControlByIdSupplier(Long idSupplier) {
		List<SimcardOrderControlEntity> entities = simcardOrderControlRepository
				.getOrderControlByIdSupplier(idSupplier);
		return entities.stream().map(SimcardOrderControlEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public SimcardOrderControlModel getOrderByIdPadre(Long idSimcardPadre) {
		SimcardOrderControlEntity entity = this.simcardOrderControlRepository.getOrderByIdPadre(idSimcardPadre);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, idSimcardPadre));
		return entity.entityToModel();
	}

	@Override
	public void add(SimcardOrderControlModel model) {
		try {
			SimcardSuppliersEntity suppliersEntity = this.simcardSuppliersRepository.findById(model.getIdSupplier())
					.orElse(null);
			if (suppliersEntity == null)
				throw new BadRequestException(
						String.format(Constants.ERROR_NOT_FOUND_RECORD_SUPPLIER, model.getIdSupplier()));

			Long noOrder = 1L;
			String batch = "04000";

			SimcardOrderControlEntity entityBatch = this.simcardOrderControlRepository.findNextBatch().orElse(null);

			SimcardOrderControlEntity findEntity = this.simcardOrderControlRepository
					.findNextNoOrderByIdSupplier(model.getIdSupplier()).orElse(null);

			/**
			 * Validamos si el proveedor tiene más un número de pedido para sumar el
			 * siguiente sino sería el primer pedido por eso se declara el noOrder = 1L
			 * 
			 */
			if (findEntity != null) {
				noOrder = findEntity.getNoOrder() + 1;
			}

			if (entityBatch != null) {
				batch = Util.buildValueFile(entityBatch.getBatch(), "1");
				batch = Util.buildCorrelative(batch, "5");
			}

			SimcardVersionEntity simcardVersionEntity = this.simcardVersionRepository
					.getCapacityByIdModel(Long.parseLong(model.getModel()));
			if (simcardVersionEntity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD_MODEL, model.getModel()));

			SimcardOrderControlEntity entity = new SimcardOrderControlEntity();
			entity.setId(-1L);
			entity.setIdSupplier(model.getIdSupplier());
			entity.setSupplierName(model.getSupplierName());
			entity.setNoOrder(noOrder);
			entity.setUserName(model.getUserName());
			entity.setCustomerName(model.getCustomerName());

			// Formar la IMSI y la ICC
			List<ConfigParameterModel> listParameters = this.configParametersService.getByIdApplication(1001L);
			Map<String, String> parameters = new HashMap<>();
			for (ConfigParameterModel parameter : listParameters) {
				parameters.put(parameter.getParameterName(), parameter.getParameterValue());
			}

			ConfigParameterEntity parameterEntity = this.configParametersRepository
					.findByParameterValue(parameters.get("MSIN"));

			String initialImsi = parameters.get("MCC").concat(parameters.get("MNC")).concat(parameters.get("MSIN"));
			String initialIcc = Util.buildInitialIcc(parameters, suppliersEntity.getSupplierNo(),
					suppliersEntity.getInitialIccd());

			String updateImsi = Util.buildValueFile(initialImsi,
					(new BigInteger(model.getOrderQuantity().toString())).add(BigInteger.ONE).toString());
			String updateIccd = Util.buildValueFile(initialIcc,
					(new BigInteger(model.getOrderQuantity().toString())).add(BigInteger.ONE).toString());

			String updateImsiParameter = Util.buildValueFile(parameters.get("MSIN"),
					model.getOrderQuantity().toString());
			String updateIccdParameter = Util.buildValueFile(suppliersEntity.getInitialIccd(),
					model.getOrderQuantity().toString());

			String initialImsiArchive = Util.imsiIccdArchive(initialImsi);
			String initialIccArchive = Util.imsiIccdArchive(initialIcc);

			entity.setInitialImsi(updateImsi);
			entity.setInitialIccd(updateIccd);

			entity.setOrderQuantity(model.getOrderQuantity());
			entity.setFileName(model.getCustomerName().trim().replace(" ", "") + batch);
			entity.setCreatedDate(LocalDateTime.now());
			entity.setCustomer(model.getCustomer());
			entity.setHlr(model.getHlr());
			entity.setBatch(batch);
			entity.setKey(model.getKey());
			entity.setType(model.getType());
			entity.setArt(model.getArt());
			entity.setGraphic(model.getGraphic());
			entity.setModel(model.getModel());
			entity.setVersionSize(model.getVersionSize());
			entity.setStatus(null);
			entity.setEmail(model.getEmail());

			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
			String orderDate = now.format(formatter);

			FileGenerator fileGenerator = new FileGenerator();
			String fileContent = fileGenerator.generateFile(model.getCustomerName().trim().replace(" ", "") + batch + Constants.FILE_EXTENSION,
					orderDate, model.getCustomerName().trim(), model.getOrderQuantity(), model.getType(), model.getArt(),
					simcardVersionEntity.getVersionDescription(), batch, model.getKey(),
					simcardVersionEntity.getCapacity(), model.getGraphic(), initialImsiArchive, initialIccArchive);

			String encodedFileContent = Base64.getEncoder().encodeToString(fileContent.getBytes());
			entity.setOrderFile(encodedFileContent);
			this.simcardOrderControlRepository.save(entity);

			parameterEntity.setParameterValue(updateImsiParameter);
			this.configParametersRepository.save(parameterEntity);

			suppliersEntity.setInitialIccd(updateIccdParameter);
			this.simcardSuppliersRepository.save(suppliersEntity);
		} catch (BadRequestException e) {
			logsService.saveLog(3, model.getIdSupplier(), Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(3, model.getIdSupplier(), Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void updateStatus(Long id) {
		try {
			SimcardOrderControlEntity entity = this.simcardOrderControlRepository.findById(id).orElse(null);
			if (entity == null) {
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
			}

			entity.setStatus("R");

			this.simcardOrderControlRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(4, id, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(4, id, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public GeneralResponse processSimcardFile(Long id, byte[] fileContent) {
		try {

			String fileContentString = new String(fileContent, StandardCharsets.UTF_8);

			Pattern fileNamePattern = Pattern.compile("File Name: (.+)");
			Matcher fileNameMatcher = fileNamePattern.matcher(fileContentString);

			if (!fileNameMatcher.find()) {
				logsService.saveLog(1, 1, "Error occurred while process SimcardFile: "
						+ buildErrorResponse("Estructura del archivo no válida"));
				return buildErrorResponse("Estructura del archivo no válida");

			}

			String fileName = fileNameMatcher.group(1);

			Pattern orderDatePattern = Pattern.compile("Order Date: (.+)");
			Pattern customerNamePattern = Pattern.compile("Customer Name: (.+)");

			Matcher orderDateMatcher = orderDatePattern.matcher(fileContentString);
			Matcher customerNameMatcher = customerNamePattern.matcher(fileContentString);

			if (orderDateMatcher.find() && customerNameMatcher.find()) {

				SimcardOrderControlEntity orderControlEntity = this.simcardOrderControlRepository.findById(id)
						.orElse(null);
				if (orderControlEntity == null)
					throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

				String base64FileContent = Base64.getEncoder().encodeToString(fileContentString.getBytes());
				orderControlEntity.setOrderDetailFile(base64FileContent);
				orderControlEntity.setStatus("P");
				simcardOrderControlRepository.save(orderControlEntity);

				return buildSuccessResponse("Archivo '" + fileName + "' procesado correctamente", null);
			} else {
				return buildErrorResponse("No se encontró el nombre del archivo en la estructura");
			}
		} catch (BadRequestException e) {
			logsService.saveLog(1, 1, "Error occurred while process SimcardFile: " + e.getMessage());
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			logsService.saveLog(1, 1, "Error occurred while process SimcardFile: " + e.getMessage());
			return buildErrorResponse("Error procesando el archivo: " + e.getMessage());
		}
	}

	private GeneralResponse buildSuccessResponse(String description, Object data) {
		GeneralResponse response = new GeneralResponse();
		response.setCode(200L);
		response.setDescription(description);
		response.setData(data);
		return response;
	}

	private GeneralResponse buildErrorResponse(String errorMessage) {
		GeneralResponse response = new GeneralResponse();
		response.setCode(500L);
		response.setDescription("Error en el proceso");
		GeneralError error = new GeneralError();
		error.setUserMessage(errorMessage);
		response.getErrors().add(error);
		return response;
	}

	@Override
	public List<SimcardOrderControlModel> getOrderControlByInitialImsi(String initialImsi) {
		List<SimcardOrderControlEntity> entities = simcardOrderControlRepository
				.getOrderControlByInitialImsi(initialImsi);
		return entities.stream().map(SimcardOrderControlEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public List<SimcardOrderControlModel> getOrderControlByInitialIccd(String initialIccd) {
		List<SimcardOrderControlEntity> entities = simcardOrderControlRepository
				.getOrderControlByInitialIccd(initialIccd);
		return entities.stream().map(SimcardOrderControlEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public OrderFilesModel getOrderFilesById(Long id) {
		return simcardOrderControlRepository.getOrderFilesById(id);
	}

}
