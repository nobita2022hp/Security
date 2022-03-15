package security.jwt.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "tblusers")
public class Account {

   @Id
    private String id;

    private String password;
    private String role;
}
