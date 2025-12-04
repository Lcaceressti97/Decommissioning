package hn.com.tigo.equipmentaccessoriesbilling.utils;

import org.springframework.data.jpa.domain.Specification;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.TypeUserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvoiceSpecifications {

	public static Specification<BillingEntity> filterByParameters(List<Long> status, List<String> warehouses,
			List<String> agencies, List<String> territories, Optional<LocalDate> startDate, Optional<LocalDate> endDate,
			TypeUserEntity typeUserEntity, List<BranchOfficesEntity> branchOfficesEntities, String seller,
			List<String> userSeeAllReport, List<String> userSeeYourBranch) {
		return (Root<BillingEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (status != null && !status.isEmpty()) {
				predicates.add(root.get("status").in(status));
			}

			if (warehouses != null && !warehouses.isEmpty()) {
				predicates.add(root.get("warehouse").in(warehouses));
			}

			if (agencies != null && !agencies.isEmpty()) {
				predicates.add(root.get("agency").in(agencies));
			}

			if (startDate.isPresent() && endDate.isPresent()) {
				predicates.add(criteriaBuilder.between(root.get("created"), startDate.get().atStartOfDay(),
						endDate.get().atTime(LocalTime.MAX)));
			}

			if (typeUserEntity != null) {
				if (userSeeAllReport != null && userSeeAllReport.contains(typeUserEntity.getTypeUser().toString())) {
				} else if (userSeeYourBranch != null
						&& userSeeYourBranch.contains(typeUserEntity.getTypeUser().toString())) {
					List<Long> branchOfficesIds = branchOfficesEntities.stream().map(BranchOfficesEntity::getId)
							.collect(Collectors.toList());
					predicates.add(root.get("idBranchOffices").in(branchOfficesIds));
				}
			}

			if (territories != null && !territories.isEmpty()) {
				Subquery<Long> subquery = query.subquery(Long.class);
				Root<BranchOfficesEntity> branchRoot = subquery.from(BranchOfficesEntity.class);
				subquery.select(branchRoot.get("id"));
				subquery.where(branchRoot.get("territory").in(territories));

				predicates.add(root.get("idBranchOffices").in(subquery));
			}

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
