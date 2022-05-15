package io.okkio.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String key;
	private String entityName;

	@Override
	public String toString() {
		return entityName + " not found: [" + key + "]";
	}

	public EntityNotFoundException(String entityName, String key) {
		super();
		this.entityName = entityName;
		this.key = key;
	}

	public EntityNotFoundException(String entityName, Long key) {
		super();
		this.entityName = entityName;
		this.key = key.toString();
	}
}