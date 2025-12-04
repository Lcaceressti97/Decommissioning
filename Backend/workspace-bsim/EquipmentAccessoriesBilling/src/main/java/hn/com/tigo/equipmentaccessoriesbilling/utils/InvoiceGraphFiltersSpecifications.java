package hn.com.tigo.equipmentaccessoriesbilling.utils;

import org.springframework.data.jpa.domain.Specification;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;

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

public class InvoiceGraphFiltersSpecifications {

	public static Specification<BillingEntity> filterByGraph(Optional<LocalDate> startDate, Optional<LocalDate> endDate,
			List<String> agencies,List<String> territories, List<String> invoiceType, List<Long> status) {
		return (Root<BillingEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (startDate.isPresent() && endDate.isPresent()) {
				predicates.add(criteriaBuilder.between(root.get("created"), startDate.get().atStartOfDay(),
						endDate.get().atTime(LocalTime.MAX)));
			}

			if (agencies != null && !agencies.isEmpty()) {
				predicates.add(root.get("agency").in(agencies));
			}

			if (invoiceType != null && !invoiceType.isEmpty()) {
				predicates.add(root.get("invoiceType").in(invoiceType));
			}

			if (status != null && !status.isEmpty()) {
				predicates.add(root.get("status").in(status));
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