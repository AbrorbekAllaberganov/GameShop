package com.example.Game.service;

import com.example.Game.entity.Game;
import com.example.Game.exceptions.ResourceNotFound;
import com.example.Game.payload.GamePayload;
import com.example.Game.payload.Result;
import com.example.Game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final AttachmentService attachmentService;
    private final Result result;

    public Result saveGame(GamePayload gamePayload) {
        try {
            Game game = new Game();
            game.setName(gamePayload.getName());
            game.setDescription(gamePayload.getDescription());
            game.setVideoUrl(gamePayload.getVideoUrl());
            game.setTorrentUrl(gamePayload.getTorrentUrl());
            game.setImageUrl(gamePayload.getImageUrl());
            game.setGener(gamePayload.getGener());
//            game.setAttachment(attachmentService.findByHashId(gamePayload.getHashId()));

            return result.success(gameRepository.save(game));
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    public Result editGame(UUID gameId, GamePayload gamePayload) {
        try {
            if (gameRepository.existsById(gameId)) {
                Game game =gameFindById(gameId);
                game.setName(gamePayload.getName());
                game.setDescription(gamePayload.getDescription());
                game.setVideoUrl(gamePayload.getVideoUrl());
                game.setTorrentUrl(gamePayload.getTorrentUrl());
                game.setImageUrl(gamePayload.getImageUrl());
                game.setGener(gamePayload.getGener());
//                game.setAttachment(attachmentService.findByHashId(gamePayload.getHashId()));

                return result.success(gameRepository.save(game));
            }else
                return result.exception(new ResourceNotFound("Game","id",gameId));
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    public Result getAll(){
        return result.success(gameRepository.findAll(Sort.by("createAt")));
    }

    public Result getById(UUID gameId){
        try {
            return result.success(gameFindById(gameId));
        }catch (Exception e){
            return result.exception(e);
        }
    }

    public Result deleteById(UUID gameId){
        try {
            gameRepository.deleteById(gameId);
            return result.delete();
        }catch (Exception e){
            return result.success(e);
        }
    }

    public Game gameFindById(UUID gameId){
        return gameRepository.findById(gameId).orElseThrow(()->new ResourceNotFound("game","id",gameId));
    }

}
