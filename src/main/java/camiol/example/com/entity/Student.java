package camiol.example.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "Student")
@ApiModel(description = "學生資料")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "學號")
	private long id;
	
	@Column(name="Name")
	@ApiModelProperty(value = "姓名")
	private String name;
	
	@Column(name="Math_Score")
	@ApiModelProperty(value = "數學成績")
	private int mathScore;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMathScore() {
		return mathScore;
	}
	public void setMathScore(int mathScore) {
		this.mathScore = mathScore;
	}
	
}
