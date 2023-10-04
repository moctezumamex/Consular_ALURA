package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> datosDetalleConsultaJacksonTester;

    @MockBean
    private AgendaDeConsultaService agendaDeConsultaService;

    @Test
    @DisplayName("Debería retornar estado 400 cuando los datos ingresados NO sean validos")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        //given //when
       var response =  mvc.perform(post("/consultas")
       ).andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Debería retornar estado 200 cuando los datos ingresados sean validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        //given
        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.CARDIOLOGIA;
        var datos = new DatosDetalleConsulta(null,2l,5l,fecha);

        //when
        when(agendaDeConsultaService.agendar(any())).thenReturn(datos);

        //given //when
        var response =  mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(null,4l,7l,fecha, especialidad)).getJson())
                ).andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.OK.value());

        var JSONesperado = datosDetalleConsultaJacksonTester.write(new DatosDetalleConsulta(null,2l,5l,fecha)).getJson();

        assertEquals(response.getContentAsString(), JSONesperado);


    }
}