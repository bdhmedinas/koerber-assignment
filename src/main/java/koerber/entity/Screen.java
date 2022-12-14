package koerber.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bruno Medinas
 *
 */

@Entity
@Table(name = "SCREEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screen {

	@Id
	@GeneratedValue
	@Column(name = "SCREEN_ID")
	private UUID screenId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CONTENT_JSON")
	private String contentJson;
}
