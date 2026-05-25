package com.Ilearn.journalApp.scheduler;

import com.Ilearn.journalApp.Entity.JournalEntry;
import com.Ilearn.journalApp.Entity.User;
import com.Ilearn.journalApp.Repository.UserRepositoryImpl;
import com.Ilearn.journalApp.cache.AppCache;
import com.Ilearn.journalApp.enums.Sentiment;
import com.Ilearn.journalApp.service.EmailService;
import com.Ilearn.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl  userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchuserandsendSaMail(){
     List<User> users= userRepository.getusersforSA();
     for( User user:users){
         List<JournalEntry> journalEntries = user.getJournalEntries();
         List<Sentiment>  sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x->x.getSentiment()).collect(Collectors.toList());
         Map<Sentiment,Integer> sentimentCounts= new HashMap<>();
         for(Sentiment sentiment:sentiments){
             if(sentiment!=null)
                 sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
         }
         Sentiment mostFrequentSentiment=null;
         int maxCount=0;
         for(Map.Entry<Sentiment,Integer> entry:sentimentCounts.entrySet()){
             if(entry.getValue()>maxCount){
                 maxCount=entry.getValue();
                 mostFrequentSentiment=entry.getKey();
             }
         }
        if(mostFrequentSentiment!=null){
            emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days",mostFrequentSentiment.toString());
        }
     }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
