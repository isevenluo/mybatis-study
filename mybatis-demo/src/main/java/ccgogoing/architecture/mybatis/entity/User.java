package ccgogoing.architecture.mybatis.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;

    private String name;

    private String sex;

    private int age;
}
