package com.cjbdi.core.configurecentent;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.yaml.snakeyaml.Yaml;

public class CommonConfig {

   private static final Logger _logger = LoggerFactory.getLogger(CommonConfig.class);
   public List timesExp;
   public List seriousInjury;
   public List minorInjury;
   public List minorMinorInjury;
   public List death;
   public List mentalDisorder;
   public List disabilityLevel;
   public List disasterRelief;
   public List administrativePunishment;
   public List criminalPunishment;
   public List forExpression;
   public List illegal;
   public List urgentNeed;
   public List onPublic;
   public List disabled;
   public List elder;
   public List lossLaborAbility;
   public List minor;
   public List student;
   public List pregnant;
   public List emergencies;
   public List pretendPolice;
   private static volatile CommonConfig config = null;
   private static volatile boolean loadError = false;


   public static Optional getConfig() {
      if(config == null && !loadError) {
         Class var0 = CommonConfig.class;
         synchronized(CommonConfig.class) {
            if(config == null && !loadError) {
               try {
                  InputStream e = CommonConfig.class.getResourceAsStream("/extractor/commons/common.yml");
                  Throwable var2 = null;

                  try {
                     Yaml yaml = new Yaml();
                     config = (CommonConfig)yaml.loadAs(e, CommonConfig.class);
                  } catch (Throwable var14) {
                     var2 = var14;
                     throw var14;
                  } finally {
                     if(e != null) {
                        if(var2 != null) {
                           try {
                              e.close();
                           } catch (Throwable var13) {
                              var2.addSuppressed(var13);
                           }
                        } else {
                           e.close();
                        }
                     }

                  }
               } catch (IOException var16) {
                  loadError = true;
                  _logger.error(var16);
               }
            }
         }
      }

      return loadError?Optional.empty():Optional.of(config);
   }

}
