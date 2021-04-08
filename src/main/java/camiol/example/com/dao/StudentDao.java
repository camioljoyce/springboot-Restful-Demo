package camiol.example.com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import camiol.example.com.entity.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long>{

}
