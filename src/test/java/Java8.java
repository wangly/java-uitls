// Copyright (C) 2017 Meituan
// All rights reserved

import com.lilian.utils.StringSpliceUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangliyue
 * @version 1.0
 * @created 17/6/14 下午10:32
 **/
public class Java8 {

    @Test(expected = NullPointerException.class)
    public void testOptional() {
        Optional optional = Optional.empty();
        assert !optional.isPresent();
        optional = Optional.ofNullable(null);
        assert !optional.isPresent();
        optional = Optional.of(null);
        assert !optional.isPresent();
    }

    @Test
    public void testCollectJoin() {
        List<String> nameList = Arrays.asList("aa", "bb");
        String nameStr = nameList.stream().collect(Collectors.joining());
        System.out.println(nameStr);
        nameStr = nameList.stream().collect(Collectors.joining(","));
        System.out.println(nameStr);
        nameStr = nameList.stream().collect(Collectors.joining(",", "{", "}"));
        System.out.println(nameStr);
    }

    @Test
    public void testGroupingBy() {
        List<User> userList = Arrays.asList(new User("aa", 1), new User("bb", 10), new User("aa", 1));
        Map<String, List<User>> nameUserMap = userList.stream().collect(Collectors.groupingBy(User::getName));
        System.out.println(nameUserMap);
        Map<Integer, List<User>> ageUserMap = userList.stream().collect(Collectors.groupingBy(User::getAge));
        System.out.println(ageUserMap);

        userList.get(0).setStudents(Arrays.asList(new User("student1", 1), new User("student2", 2)));
        Long time1 = System.currentTimeMillis();
        Integer result1 = userList.parallelStream().map(User::getAge)
                .collect(Collectors.summarizingInt(item -> item.intValue())).getMax();
        Long time2 = System.currentTimeMillis();
        Integer result2 = userList.parallelStream().map(User::getAge)
                .collect(Collectors.summarizingInt(item -> item.intValue())).getMax();
        Long time3 = System.currentTimeMillis();
        System.out.println(StringSpliceUtils.splice("resutl1:{}, time:{}", result1, time2-time1));
        System.out.println(StringSpliceUtils.splice("resutl2:{}, time:{}", result2, time3-time2));
        userList.stream().flatMap(User::getStudentStream).mapToInt(User::getAge).sum();

    }

    static class User {
        private String name;
        private Integer age;
        private List<User> students;

        public User() {
        }

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public List<User> getStudents() {
            return students;
        }

        public void setStudents(List<User> students) {
            this.students = students;
        }

        public Stream<User> getStudentStream() {
            if (students != null) {
                students = new ArrayList<>();
//                return Stream.of(students);
            }
            return null;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("User{");
            sb.append("name='").append(name).append('\'');
            sb.append(", age=").append(age);
            sb.append(", students=").append(students);
            sb.append('}');
            return sb.toString();
        }
    }
}