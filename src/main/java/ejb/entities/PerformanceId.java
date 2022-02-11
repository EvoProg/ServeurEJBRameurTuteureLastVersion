package ejb.entities;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

/*
    Classe regroupant les PRIMARY KEY de la table "performances".
 */
@Embeddable
public class PerformanceId implements Serializable {
    //Déclaration des variables
    private static final long serialVersionUID = -5092927882070347249L;
    @Column(name = "id_session", nullable = false)
    private Integer idSession;
    @Column(name = "id_util", nullable = false)
    private Integer idUtil;
    @Column(name = "\"timestamp\"", nullable = false)
    private long timestamp;

    //Accesseurs en écriture et en lecture
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getIdUtil() {
        return idUtil;
    }

    public void setIdUtil(Integer idUtil) {
        this.idUtil = idUtil;
    }

    public Integer getIdSession() {
        return idSession;
    }

    public void setIdSession(Integer idSession) {
        this.idSession = idSession;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSession, idUtil, timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PerformanceId entity = (PerformanceId) o;
        return Objects.equals(this.idSession, entity.idSession) &&
                Objects.equals(this.idUtil, entity.idUtil) &&
                Objects.equals(this.timestamp, entity.timestamp);
    }
}