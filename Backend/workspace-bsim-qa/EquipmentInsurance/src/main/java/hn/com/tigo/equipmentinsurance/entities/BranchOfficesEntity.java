package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.BranchOfficesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_BRANCH_OFFICES")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchOfficesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_POINT")
	private Long idPoint;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "ADDRESS", length = 500)
	private String address;

	@Column(name = "PHONE", length = 12)
	private String phone;

	@Column(name = "RTN", length = 20)
	private String rtn;

	@Column(name = "FAX", length = 200)
	private String fax;

	@Column(name = "PBX", length = 200)
	private String pbx;

	@Column(name = "EMAIL", length = 100)
	private String email;

	@Column(name = "ACCTCODE", length = 30)
	private String acctCode;

	@Column(name = "FICTITIOUS_NUMBER", length = 50)
	private String fictitiousNumber;

	@Column(name = "ID_COMPANY")
	private Long idCompany;

	@Column(name = "ID_SYSTEM")
	private Long idSystem;

	@Column(name = "WINERY_CODE")
	private String wineryCode;

	@Column(name = "WINERY_NAME")
	private String wineryName;

	@Column(name = "TERRITORY")
	private String territory;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	@Column(name = "WAREHOUSE_MANAGER")
	private String wareHouseManager;

	public BranchOfficesModel entityToModel() {
		BranchOfficesModel model = new BranchOfficesModel();
		model.setId(this.getId());
		model.setIdPoint(this.getIdPoint());
		model.setName(this.getName());
		model.setAddress(this.getAddress());
		model.setPhone(this.getPhone());
		model.setRtn(this.getRtn());
		model.setFax(this.getFax());
		model.setPbx(this.getPbx());
		model.setEmail(this.getEmail());
		model.setAcctCode(this.getAcctCode());
		model.setFictitiousNumber(this.getFictitiousNumber());
		model.setIdCompany(this.getIdCompany());
		model.setIdSystem(this.getIdSystem());
		model.setWineryCode(this.getWineryCode());
		model.setWineryName(this.getWineryName());
		model.setTerritory(this.getTerritory());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		model.setWareHouseManager(this.getWareHouseManager());

		return model;
	}
}
