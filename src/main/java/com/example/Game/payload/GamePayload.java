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
    private String url;
    private String hashId;
}
