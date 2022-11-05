package koerber.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bruno Medinas
 *
 */

@Entity
@Table(name = "MY_FILTER")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyFilter {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "id")
	@Column(name = "USER_ID")
	private User userId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DATA")
	private String data;

	@Column(name = "OUTPUT_FILTER")
	private String outputFilter;

	@ManyToOne
	@JoinColumn(name = "id")
	@Column(name = "SCREEN_ID")
	private Screen screenId;
	
	@Column(name = "DEPRECATED")
	private Boolean deprecated;

}
