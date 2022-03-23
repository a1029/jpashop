package jpabook.jpashop.controller;

import jpabook.jpashop.dto.HelloDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello/{name}")
    public HelloDto helloGet(@PathVariable String name) {
        return new HelloDto(name, 20);
    }

    @PostMapping("/hello")
    public String helloPost(@RequestBody HelloDto helloDto) {
        return helloDto.getName() + " " + helloDto.getAge();
    }
}
