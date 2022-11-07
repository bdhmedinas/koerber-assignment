package koerber.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * @author Bruno Medinas
 *
 */

@MappedSuperclass
@Data
public abstract class Filter {

	@Id
	@GeneratedValue
	@Column(name = "FILTER_ID")
	private UUID filterId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private AssignmentUser user;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DATA")
	private String data;

	@Column(name = "OUTPUT_FILTER")
	private String outputFilter;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SCREEN_ID")
	private Screen screen;
	
	@Column(name = "TIMESTAMP")
	private Long timestamp;
	
	@Column(name = "VERSION")
	private Integer version;
	
	@Column(name = "VERSION_CORRELATION")
	private UUID versionCorrelation;
}
