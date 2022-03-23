package jpabook.jpashop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.dto.HelloDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(HelloController.class)
@AutoConfigureRestDocs
class HelloControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }
    @Test
    void helloGetTest() throws Exception {
        mockMvc.perform(get("/hello/kim").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("hello", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("name").description("이름 필드 입니다."),
                                PayloadDocumentation.fieldWithPath("age").description("나이 필드 입니다.")
                        )));
    }

    @Test
    void helloPostTest() throws Exception {
        HelloDto helloDto = new HelloDto("kim", 20);
        String content = objectMapper.writeValueAsString(helloDto);
        mockMvc.perform(post("/hello")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("kim 20"))
                .andDo(document("helloPost"));
    }
}