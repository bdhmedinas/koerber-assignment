package koerber.entity;

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
@Table(name = "BRANCH")
@Getter
@Setter
public class Branch extends Filter {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MY_FILTER_ID", referencedColumnName = "FILTER_ID")
	private MyFilter filter;
}
