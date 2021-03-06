package tests;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.Micro.Services.reviewservice.ReviewServiceApplication;
import com.Micro.Services.reviewservice.entities.Review;
import com.Micro.Services.reviewservice.exceptions.EntityNotFoundException;
import com.Micro.Services.reviewservice.services.ReviewService;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReviewServiceApplication.class)
public class ReviewOperationsTest {

	@Autowired
	ReviewService reviewService;

	private static MongodExecutable mongodExe;
	private static MongodProcess mongod;
	private static MongoClient mongo;

	// This is a bypass for spring data mongo repositories to work at test runtime
	static {
		try {
			MongodStarter starter = MongodStarter.getDefaultInstance();
			String bindIp = "localhost";
			int port = 27017;
			IMongodConfig mongodConfig;
			mongodConfig = new MongodConfigBuilder().version(Version.Main.V3_6)
					.net(new Net(bindIp, port, Network.localhostIsIPv6())).build();
			mongodExe = starter.prepare(mongodConfig);
			mongod = mongodExe.start();
			mongo = new MongoClient(bindIp, port);
			Logger.getAnonymousLogger().info("EMBEDDED MONGO STARTED ON PORT : " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterClass() throws Exception {
		if (mongod != null) {
			mongod.stop();
			mongodExe.stop();
		}
	}

	// FAILS ON CIRCLE CI BECAUSE OF ENVIRONMENT SETUP, UNCOMMENT TO RUN LOCALLY
//	@Test(expected = EntityNotFoundException.class)
	public void INSERT_ShouldFailWhenProductDoesNotExist() throws EntityNotFoundException {
		Review review = Review.builder().productId("MISSING_PRODUCT").build();
		reviewService.insert(review);
	}

	@Test(expected = EntityNotFoundException.class)
	public void INSERT_ShouldFailWhenProductIdNotProvided() throws EntityNotFoundException {
		Review review = Review.builder().productId("").build();
		reviewService.insert(review);
	}

	@Test(expected = EntityNotFoundException.class)
	public void DELETE_ShouldFailWhenReviewDoesNotExist() throws EntityNotFoundException {
		reviewService.deleteReview("MISSING_PRODUCT");
	}

	@Test(expected = EntityNotFoundException.class)
	public void DELETE_ShouldFailWhenReviewIdNotProvided() throws EntityNotFoundException {
		reviewService.deleteReview("");
	}

	// FAILS ON CIRCLE CI BECAUSE OF ENVIRONMENT SETUP, UNCOMMENT TO RUN LOCALLY
//	@Test(expected = EntityNotFoundException.class)
	public void UPDATE_ShouldFailWhenProductDoesNotExist() throws EntityNotFoundException {
		Review review = Review.builder().productId("MISSING_PRODUCT").build();
		reviewService.save(review);
	}

	@Test(expected = EntityNotFoundException.class)
	public void UPDATE_ShouldFailWhenProductIdNotProvided() throws EntityNotFoundException {
		Review review = Review.builder().productId("").build();
		reviewService.save(review);
	}

}
