package io.okkio.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity {
	@CreatedBy
	@Column(
			name = "CREATED_BY",
			updatable = false
	)
	@JsonProperty("created_by")
	private String createdBy;
	@CreatedDate
	@Column(
			name = "CREATED_AT",
			nullable = false,
			updatable = false
	)
	@JsonProperty("created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	@LastModifiedBy
	@Column(
			name = "UPDATED_BY"
	)
	@JsonProperty("updated_by")
	private String updatedBy;
	@LastModifiedDate
	@Column(
			name = "UPDATED_AT",
			nullable = false
	)
	@JsonProperty("updated_at")
	private LocalDateTime updatedAt = LocalDateTime.now();

	public AbstractAuditingEntity() {
	}
}