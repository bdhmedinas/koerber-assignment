package koerber.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bruno Medinas
 *
 */

@Entity
@Table(name = "DELETE_FILTER")
@Getter
@Setter
public class DeletedFilter extends Filter {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ACTION_USER_ID", referencedColumnName = "USER_ID")
	private AssignmentUser actionUser;
	
	@Column(name = "DELETE_TIMESTAMP")
	private Long deleteTimestamp;
}
