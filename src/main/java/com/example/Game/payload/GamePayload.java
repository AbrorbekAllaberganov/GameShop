package com.example.Game.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GamePayload {
    private String name;
    private String description;
    private String videoUrl;
    private String torrentUrl;
    private String imageUrl;
//    private String hashId;
}
