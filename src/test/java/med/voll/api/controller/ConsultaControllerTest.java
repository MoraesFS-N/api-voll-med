package med.voll.api.controller;

import med.voll.api.domain.model.EnumEspecialidade;
import med.voll.api.domain.model.GetConsultaDTO;
import med.voll.api.domain.model.PostConsultaDTO;
import med.voll.api.domain.service.ConsultaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<PostConsultaDTO> postConsultaDTOJacksonTester;

    @Autowired
    private JacksonTester<GetConsultaDTO> getConsultaDTOJacksonTester;

    @MockBean
    private ConsultaService service;

    @Test
    @DisplayName("Retorno http 400 informações inválidas")
    @WithMockUser
    void agendarErro400() throws Exception {
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/consultas")).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Retorno http 200 informações válidas")
    @WithMockUser
    void agenderCode200() throws Exception {

        LocalDateTime data = LocalDateTime.now().plusHours(1);
        EnumEspecialidade espec = EnumEspecialidade.CARDIOLOGIA;

        GetConsultaDTO getConsultaDTO = new GetConsultaDTO(null, 2L, 5L, data);

        Mockito.when(service.agendar(any())).thenReturn(getConsultaDTO);

        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders
                        .post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postConsultaDTOJacksonTester.write(
                                new PostConsultaDTO(2L, 5L, data, espec)
                                ).getJson())
                )
                .andReturn()
                .getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String jsonExpected = getConsultaDTOJacksonTester.write(
                getConsultaDTO
        ).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonExpected);
    }
}