package no.uis.security.common.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Entity<PK extends Serializable> implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private PK id;
    /**
     * Just for Optimistic locking
     */
    @Version
    private Long version;

	public PK getId() {
		return id;
	}
	

	public void setId(PK id) {
		this.id = id;
	}

	public Long getVersion(){
		return this.version;
	}
	
	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

}
