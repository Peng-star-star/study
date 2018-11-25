package com.example.springBoot.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springBoot.entity.Student;

/**
 * 按照RESTful API编写
 * @author peng
 *
 */
@RestController 
@RequestMapping(value="/students")
public class StudentController {
	
	//用于存储数据
	static Map<String, Student> students = Collections.synchronizedMap(new HashMap<String, Student>());
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Student> getStudentList(){
		List<Student> r = new ArrayList<Student>(students.values()); 
		return r;
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String postStudent(@ModelAttribute Student student){
		students.put(student.getId(), student);
		return "success";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Student getStudent(@PathVariable String id){
		return students.get(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT) 
    public String putStudent(@PathVariable String id, @ModelAttribute Student student) { 
		Student u = students.get(id); 
        u.setName(student.getName()); 
        u.setAge(student.getAge()); 
        u.setBirthday(student.getBirthday());
        students.put(id, u); 
        return "success"; 
    } 
 
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE) 
    public String deleteUser(@PathVariable String id) { 
    	students.remove(id); 
        return "success"; 
    } 
}
