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
@Table(name = "ASSIGNMENT_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentUser {
	
	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private UUID userId;
	
	@Column(name = "NAME")
	private String name;
}
