package guru.sfg.brewery.models.security;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by jt on 7/20/20.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "logins_success")
/*
// after update is done
Hibernate: create table logins_success (id integer not null, created_date timestamp, last_modified_date timestamp, source_ip varchar(255), user_id integer, primary key (id))
Hibernate: alter table logins_success add constraint FK3eyqih04mxp53bwjyhxlf8up1 foreign key (user_id) references users()
fix alter table logins_success add  foreign key (user_id) references users(id)
*/
public class LoginSuccess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    private String sourceIp;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;
}