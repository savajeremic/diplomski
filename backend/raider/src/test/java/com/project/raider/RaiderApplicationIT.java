package com.project.raider;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.project.raider.dto.GameDto;
import com.project.raider.service.GameService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RaiderApplicationIT {

    @MockBean
    private GameService gameService;
    private List<GameDto> gameDtoList;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        gameDtoList = gameService.getAllGames();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @DisplayName("Application loaded")
    void webAppContextLoads(){
    }

    @Test
    @DisplayName("Getting all game reviews")
    public void getAllGameReviewsTest() throws Exception {
        given(gameService.getAllGames()).willReturn(gameDtoList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/store/{id}/reviews", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Getting game")
    public void getGameTest() throws Exception {
        GameDto game = new GameDto();
        game.setId(123);
        game.setName("GameServiceMockUnitTest");

        given(gameService.getById(game.getId())).willReturn(game);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/store/{id}", 123)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Getting all games")
    public void getGamesTest() throws Exception {
        given(gameService.getAllGames()).willReturn(gameDtoList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/store")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
