package com.enotes.webservices;

import com.enotes.service.WordleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordleWS {

    @Autowired
    private WordleService wordleService;
    
    public WordleWS() {
    }

    @GetMapping("/guess")
    public Result guess(String word) {
        String error = wordleService.validate(word);
        if (error != null) {
            return new Result(null, error);
        }
        return new Result(wordleService.calculateResults(word), null);
    }
}
