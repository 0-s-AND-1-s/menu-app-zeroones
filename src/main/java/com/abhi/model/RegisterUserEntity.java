package com.abhi.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


@Entity
@Table(name = "RestaurantAppUsers")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterUserEntity {

    @Id
    @SequenceGenerator(
            name = "user_id_seq",
            sequenceName = "user_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Integer userId;

    private String userName;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER) // Correctly marks this as a collection of simple types
    @CollectionTable(
            name = "user_authorities", // table name
            joinColumns = @JoinColumn(
                    name = "user_id", //  FK column in the 'user_authorities' table
                    referencedColumnName = "userId" // References the PK column of the owning table
            )
    )
    @Column(name = "authority_name") // Defines the name of the column that stores the Set<String> value
    private Set<String> authorityType;
}

/*The @ElementCollection annotation is necessary because it tells JPA (Java Persistence API) that you are mapping a collection
 of simple values or embeddable objects, not a collection of other entities.

If you omit @ElementCollection, JPA will assume that your Set<String> field is a one-to-many relationship with another entity.
Since String is not a defined entity class, the mapping will fail.

Here is why @ElementCollection is essential for your use case:
Annotation	        Purpose
@ElementCollection--->	Tells JPA: "The authorityType field is a collection whose elements are not separate entities (like a Role class)
                        but are simple values (String) that belong to the owning entity."
The Result	  --->      JPA uses the configuration in @CollectionTable to automatically manage a separate join table to store these values,
                        linking them back to the original user record using a foreign key.
* */

