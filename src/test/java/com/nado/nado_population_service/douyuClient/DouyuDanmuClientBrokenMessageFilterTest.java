package com.nado.nado_population_service.douyuClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.nado.nado_population_service.util.CommonUtil.*;
import static org.junit.Assert.*;

import java.util.List; 
@RunWith(SpringRunner.class)
@SpringBootTest
public class DouyuDanmuClientBrokenMessageFilterTest {
	@Autowired
	private DouyuDanmuClient client;
	@Autowired
	private DouyuDanmuBrokenMessageFilter clientWrapper;
	@Before
	public void Before() throws InterruptedException{
		//client.register(2020877+"");
	}
	@After
	public void after() throws InterruptedException {
		//client.logout();
		//client.clear();
	}
	@Test
	public void alwaysPassTest(){
		assertTrue(true);
	}
	@Test
	public void isBrokenMessageIfTypeLess(){
		String message = "/rid@=2020877/gfid@=824/gs@=2/uid@=178767330/nn@=鍠滄绾宠眴鐨勫皬濂冲/ic@=avanew@Sface@S201711@S25@S23@S64eea5c31ca1ce0813fbb845bb7dcb24/eid@=0/level@=10/dw@=91833500/hits@=3/ct@=0/el@=/cm@=0/bnn@=璞嗛湼闇?bl@=7/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/sahf@=0/fc@=0/timestamp@=1513582632026/messageId@=55/";
		assertTrue(clientWrapper.isBrokenMessage(message)!=-1);
	}
	@Test
	public void isNotBrokenMessageIfItIsAGoodMessage(){
		String message = "/type@=dgb/rid@=2020877/gfid@=824/gs@=2/uid@=178767330/nn@=鍠滄绾宠眴鐨勫皬濂冲/ic@=avanew@Sface@S201711@S25@S23@S64eea5c31ca1ce0813fbb845bb7dcb24/eid@=0/level@=10/dw@=91833500/hits@=3/ct@=0/el@=/cm@=0/bnn@=璞嗛湼闇?bl@=7/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/sahf@=0/fc@=0/timestamp@=1513582632026/messageId@=55/";
		assertTrue("This message is actually NOT broken.",-1==clientWrapper.isBrokenMessage(message));
	} 
	@Test
	public void isBrokenMessageIfNotEndsInSlash(){
		String message = "/type@=dgb/rid@=2020877/gfid@=824/gs@=2/uid@=178767330/nn@=鍠滄绾宠眴鐨勫皬濂冲/ic@=avanew@Sface@S201711@S25@S23@S64eea5c31ca1ce0813fbb845bb7dcb24/eid@=0/level@=10/dw@=91833500/hits@=3/ct@=0/el@=/cm@=0/bnn@=璞嗛湼闇?bl@=7/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/sahf@=0/fc@=0/timestamp@=1513582632026/messageId@=55";
		assertTrue(clientWrapper.isBrokenMessage(message)>0);		
	}
	@Test
	public void isBrokenMessageIfMultipleTypeFieldsAreDetected(){
		String message = "/type@=dgb/rid@=2020877/gfid@=824/gs@=2/uid@=178767330/nn@=鍠滄绾宠眴鐨勫皬濂冲/ic@=avanew@Sface@S201711@S25@S23@S64eea5c31ca1ce0813fbb845bb7dcb24/eid@=0/level@=10/dw@=91833500/hits@=3/ct@=0/el@=/cm@=0/bnn@=璞嗛湼闇?bl@=7/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/sahf@=0/fc@=0/timestamp@=1513582632026/messageId@=55/";
		assertTrue(clientWrapper.isBrokenMessage(message+message)>0);
	}
	

	private void testForAllFieldsExist(String template, String sample){
		String type = matchStringValue(template, "type");
		for(String field:extractFields(template)){
			assertTrue(field + " is missing for type:"+type, sample.contains(field));	
		}
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_chatmsg(){
		String template = "/type@=chatmsg/rid@=58839/uid@=123456/nn@=test/txt@=666/cid@=1111/ic@=icon/sahf@=0/level@=1/bnn@=test/bl@=0/brid@=58839/hc@=0/el@=eid@AA=1@ASetp@AA=1@ASsc@AA=1@AS/";
		String sample1 = "/type@=chatmsg/rid@=2020877/ct@=2/uid@=21162702/nn@=豆子丶小粉丝/txt@=[emot:dy118][emot:dy118]/cid@=9f5da093f87c4402d0a6000000000000/ic@=avanew@Sface@S201712@S04@S01@S3d4ff644286c1d79c486244599f011f3/level@=28/sahf@=0/nl@=7/col@=4/dlv@=3/dc@=2/bdlv@=3/bnn@=豆霸霸/bl@=15/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/hl@=1/ifs@=1/el@=/timestamp@=1513585316607/messageId@=638/";
		String sample2 = "/type@=chatmsg/rid@=2020877/uid@=9749085/nn@=likaka/txt@=评完可以和小白鸽一起吃鸡/cid@=9f5da093f87c440293ac000000000000/ic@=avanew@Sface@S201612@S18@S20@S1736cb1fbcfa7409a7cd71546e7c710c/level@=8/sahf@=0/bnn@=/bl@=0/brid@=0/hc@=/el@=/timestamp@=1513593371019/messageId@=57/";
		testForAllFieldsExist(template, sample1);
		testForAllFieldsExist(template, sample2);
	}
	@Test
	public void messageWithNewLineCharacterIsGoodMessage_gpbc(){
		String sample ="/type@=chatmsg/rid@=2020877/uid@=142742260/nn@=兜兜呢没糖糖/txt@=赠送失败\n鱼翅不足/cid@=9f5da093f87c44027c73010000000000/ic@=avanew@Sface@S201712@S17@S03@S97a0c1e47a9822a45e168c445516a916/level@=18/sahf@=0/nl@=7/col@=3/dlv@=1/dc@=1/bdlv@=1/bnn@=豆霸霸/bl@=11/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/ifs@=1/el@=eid@AA=1500000113@ASetp@AA=1@ASsc@AA=1@ASef@AA=0@AS@Seid@AA=1500000171@ASetp@AA=2@ASsc@AA=1@ASef@AA=0@AS@Seid@AA=1500000172@ASetp@AA=1@ASsc@AA=1@ASef@AA=0@AS@Seid@AA=1500000173@ASetp@AA=5@ASsc@AA=1@ASef@AA=0@AS@S/timestamp@=1513605745018/messageId@=40291/";
		assertTrue(clientWrapper.isBrokenMessage(sample)!=1);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=2);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=3);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=4);
		assertTrue(clientWrapper.isBrokenMessage(sample)<0);
	}
	@Test
	public void goodMessage1_gpbc(){
		String sample = "/type@=gpbc/cnt@=1/sid@=136205801/did@=15459592/rpt@=0/snk@=豆子的柠檬茶/dnk@=没错是小丝/pnm@=赞/rid@=2020877/timestamp@=1513605815031/messageId@=44512/";
		assertTrue(clientWrapper.isBrokenMessage(sample)!=1);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=2);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=3);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=4);
		assertTrue(clientWrapper.isBrokenMessage(sample)<0);
	}
	
	@Test
	public void goodMessage1_chatmsg(){
		String sample = "/type@=chatmsg/rid@=2020877/ct@=1/uid@=10668500/nn@=纯丶丶粹/txt@=[emot:dy127]/cid@=9f5da093f87c44022aac000000000000/ic@=avanew@Sface@S201706@S15@S03@S50a162ca598c6234be45c196f8ee638d/level@=17/sahf@=0/bnn@=7911/bl@=5/brid@=7911/hc@=73579b20ca765d94dc96e17288e64f64/el@=/timestamp@=1513592565294/messageId@=32/";
		assertTrue(clientWrapper.isBrokenMessage(sample)==-1);
	}
	@Test
	public void goodMessage2_chatmsg(){
		String sample = "/type@=chatmsg/rid@=2020877/uid@=9749085/nn@=likaka/txt@=评完可以和小白鸽一起吃鸡/cid@=9f5da093f87c440293ac000000000000/ic@=avanew@Sface@S201612@S18@S20@S1736cb1fbcfa7409a7cd71546e7c710c/level@=8/sahf@=0/bnn@=/bl@=0/brid@=0/hc@=/el@=/timestamp@=1513593371019/messageId@=57/";
		assertTrue(clientWrapper.isBrokenMessage(sample)!=1);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=2);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=3);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=4);
		assertTrue(clientWrapper.isBrokenMessage(sample)<0);
	}
	@Test
	public void goodMessage1_synexp(){
		String sample = "/type@=synexp/o_exp@=14053053060/o_lev@=91/o_minexp@=13954500000/o_upexp@=801446940/rid@=2020877/timestamp@=1513593136037/messageId@=20/";
		assertTrue(clientWrapper.isBrokenMessage(sample)==-1);
	}
	@Test
	public void goodMessage1_onlinegift(){
		String sample = "/timestamp@=1513616463022/messageId@=148314/type@=onlinegift/rid@=2020877/gid@=0/uid@=10007922/sil@=177/nn@=酷狗自己人/level@=37/ur@=1/if@=6/btype@=6/ct@=0/";
		assertTrue(clientWrapper.isBrokenMessage(sample)!=1);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=2);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=3);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=4);
		assertTrue(clientWrapper.isBrokenMessage(sample)<0);
	}
	@Test
	public void goodMessage1_newblackres(){
		String sample = "/timestamp@=1513608150024/messageId@=89329/type@=newblackres/ret@=0/rid@=2020877/gid@=0/otype@=1/sid@=9853292/did@=181267795/snic@=TinyTank丶/dnic@=N3v4z6A6/endtime@=1513694549/";
		assertTrue(clientWrapper.isBrokenMessage(sample)!=1);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=2);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=3);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=4);
		assertTrue(clientWrapper.isBrokenMessage(sample)<0);
	}
	@Test
	public void goodMessage1_ggbb(){
		String sample = "/timestamp@=1513606907034/messageId@=62469/type@=ggbb/rid@=2020877/gid@=0/gt@=0/sl@=131/sid@=107697091/did@=97803784/snk@=透明元素水晶豆/dnk@=Fantomas光着腚/rpt@=0/";
		assertTrue(clientWrapper.isBrokenMessage(sample)!=1);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=2);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=3);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=4);
		assertTrue(clientWrapper.isBrokenMessage(sample)<0);
	}
	@Test
	public void goodMessage1_anbc(){
		String sample = "/timestamp@=1513606551024/messageId@=57508/type@=anbc/rid@=2020877/gid@=0/bt@=2/uid@=76717375/hrp@=0/unk@=大头丿脑壳医生/uic@=avanew@Sface@S201710@S26@S20@S12b3971ace4dea6f7794cd6fb21e3ce4/drid@=2020877/donk@=纳豆nado/nl@=7/ts@=1513606551/";
		assertTrue(clientWrapper.isBrokenMessage(sample)!=1);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=2);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=3);
		assertTrue(clientWrapper.isBrokenMessage(sample)!=4);
		assertTrue(clientWrapper.isBrokenMessage(sample)<0);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_anbc(){
		
		String template = "/timestamp@=1513611088023/messageId@=123772/type@=anbc/rid@=2020877/gid@=0/bt@=2/uid@=37216637/hrp@=0/unk@=南风之环/uic@=avatar@S037@S21@S66@S37_avatar/drid@=2020877/donk@=纳豆nado/nl@=7/ts@=1513611088/";
		String sample = "/timestamp@=1513606551024/messageId@=57508/type@=anbc/rid@=2020877/gid@=0/bt@=2/uid@=76717375/hrp@=0/unk@=大头丿脑壳医生/uic@=avanew@Sface@S201710@S26@S20@S12b3971ace4dea6f7794cd6fb21e3ce4/drid@=2020877/donk@=纳豆nado/nl@=7/ts@=1513606551/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_onlinegift(){
		
		String template = "/type@=onlinegift/rid@=1/uid@=1/gid@=-9999/sil@=1/if@=1/ct@=1/nn@=tester/ur@=1/level@=6/btype@=1/";
		String sample1 = "/type@=onlinegift/rid@=2020877/gid@=0/uid@=56848765/sil@=166/nn@=水光潋滟不修仙/level@=34/ur@=1/if@=6/btype@=6/ct@=0/timestamp@=1513584749913/messageId@=301/";
		String sample2 = "/timestamp@=1513616463022/messageId@=148314/type@=onlinegift/rid@=2020877/gid@=0/uid@=10007922/sil@=177/nn@=酷狗自己人/level@=37/ur@=1/if@=6/btype@=6/ct@=0/";
		testForAllFieldsExist(template, sample1);
		testForAllFieldsExist(template, sample2);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_dgb(){
		
		String template = "/type@=dgb/gfid@=1/gs@=59872/uid@=1/rid@=1/nn@=someone/level@=1/dw@=1/";
		String sample = "/type@=dgb/rid@=2020877/gfid@=191/gs@=1/uid@=53020866/nn@=豆子赐名的碧瑶你真非/ic@=avanew@Sface@S201710@S25@S18@S52ce8f1e08f9ef33e40c21aab51b2610/eid@=0/level@=50/dw@=91833500/dlv@=3/dc@=71/bdlv@=3/rg@=4/nl@=3/ct@=0/el@=/cm@=0/bnn@=豆霸霸/bl@=26/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/sahf@=0/fc@=0/timestamp@=1513584812420/messageId@=319/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_uenter(){
		
		String template = "/type@=uenter/rid@=1/uid@=1/nn@=someone/level@=1/el@=eid@AA=1@ASetp@AA=1@ASsc@AA=1@AS@S/";
		String sample = "/type@=uenter/rid@=2020877/uid@=32629005/nn@=那就做你的萤火虫/level@=25/ic@=avanew@Sface@S201707@S21@S22@S60d22d5da86d6f35e6f6949a259200a0/rni@=0/el@=/sahf@=0/wgei@=0/timestamp@=1513584763152/messageId@=305/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_bc_buy_deserve(){
		String template = "/type@=bc_buy_deserve/rid@=1/gid@=-9999/level@=1/cnt@=1/hits@=1/lev@=1/sui@=用户信息序列化/";
		String sample = "/type@=bc_buy_deserve/level@=36/lev@=3/rid@=2020877/gid@=0/cnt@=1/hits@=1/sid@=22528912/sui@=id@A=22528912@Sname@A=qq_lmTFQhl8@Snick@A=ssnaoki@Sicon@A=avanew@ASface@AS201711@AS29@AS22@ASa690bed6875780f6d5ac8da1f4d37f90@Srg@A=1@Spg@A=1@Srt@A=1440497378@Sbg@A=0@Sweight@A=0@Scps_id@A=0@Sps@A=1@Ses@A=1@Sver@A=20150929@Sglobal_ban_lev@A=0@Sexp@A=15552600@Slevel@A=36@Scurr_exp@A=1684800@Sup_need@A=227800@Sgt@A=0@Sit@A=0@Sits@A=0@Scm@A=0@Srni@A=0@Shcre@A=0@Screi@A=0@Sel@A=@Shfr@A=0@Sfs@A=1@S/sahf@=0/bnn@=豆霸霸/bl@=20/brid@=2020877/hc@=fe329c65ab6c55c8c8f38e12bb502a41/timestamp@=1513606421032/messageId@=55277/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_rss(){
		String template = "/type@=rss/rid@=1/gid@=1/ss@=1/code@=1/rt@=0/notify@=1/endtime@=1/";
		String sample = "/type@=rss/rt@=0/rtv@=0/rid@=2020877/gid@=0/ss@=0/code@=1/notify@=0/endtime@=1513609531/timestamp@=1513609540025/messageId@=116337/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_ranklist(){
		String template = "/type@=ranklist/rid@=1/gid@=-9999/list_all@=榜单结构体/list@=榜单结构体/list_day@=榜单结构体/";
		String sample = "/type@=ranklist/rid@=2020877/gid@=0/list@=uid@AA=536351@AScrk@AA=1@ASlrk@AA=2@ASrs@AA=1@ASnickname@AA=隔壁村八大爷@ASgold@AA=200000@ASlevel@AA=120@ASicon@AA=avanew@AASface@AAS201711@AAS30@AAS21@AASfe3ef304fe43054b78005acc5c7d1e15@ASpg@AA=1@ASrg@AA=4@ASne@AA=6@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=9853292@AScrk@AA=2@ASlrk@AA=1@ASrs@AA=-1@ASnickname@AA=TinyTank丶@ASgold@AA=140000@ASlevel@AA=42@ASicon@AA=avanew@AASface@AAS201712@AAS16@AAS02@AASf6bcede14e11946f07908e1e02694b44@ASpg@AA=1@ASrg@AA=4@ASne@AA=3@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=47007789@AScrk@AA=3@ASlrk@AA=32767@ASrs@AA=1@ASnickname@AA=咯咯哟Zzz@ASgold@AA=54300@ASlevel@AA=46@ASicon@AA=avatar@AASface@AAS201605@AAS21@AAS373f25ce444df6803098c157d57e30f9@ASpg@AA=1@ASrg@AA=4@ASne@AA=3@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=9744044@AScrk@AA=4@ASlrk@AA=6@ASrs@AA=1@ASnickname@AA=带带小坦克丶Nick@ASgold@AA=23600@ASlevel@AA=46@ASicon@AA=avanew@AASface@AAS201712@AAS01@AAS22@AAS40366ac8bcf092bd3a95e47d48f85ecc@ASpg@AA=1@ASrg@AA=4@ASne@AA=1@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=29996122@AScrk@AA=5@ASlrk@AA=4@ASrs@AA=-1@ASnickname@AA=惗 惗卜忘@ASgold@AA=10000@ASlevel@AA=29@ASicon@AA=avatar@AAS029@AAS99@AAS61@AAS22_avatar@ASpg@AA=1@ASrg@AA=1@ASne@AA=1@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=155412194@AScrk@AA=6@ASlrk@AA=5@ASrs@AA=-1@ASnickname@AA=乜柒都唔洗讲@ASgold@AA=10000@ASlevel@AA=23@ASicon@AA=avanew@AASface@AAS201712@AAS04@AAS14@AAS10d7dbfa63134cb638a2c4e9ab6ef3fb@ASpg@AA=1@ASrg@AA=1@ASne@AA=7@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=113969068@AScrk@AA=7@ASlrk@AA=11@ASrs@AA=1@ASnickname@AA=骨灰盒007@ASgold@AA=6200@ASlevel@AA=9@ASicon@AA=avanew@AASface@AAS201709@AAS05@AAS00@AAS0bf2213c382a03c2d1e5301c0f05c34e@ASpg@AA=1@ASrg@AA=1@ASne@AA=0@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=178767330@AScrk@AA=8@ASlrk@AA=32767@ASrs@AA=1@ASnickname@AA=喜欢纳豆的小女孩@ASgold@AA=6000@ASlevel@AA=10@ASicon@AA=avanew@AASface@AAS201711@AAS25@AAS23@AAS64eea5c31ca1ce0813fbb845bb7dcb24@ASpg@AA=1@ASrg@AA=1@ASne@AA=0@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=16731996@AScrk@AA=9@ASlrk@AA=8@ASrs@AA=-1@ASnickname@AA=浅析头大的优势@ASgold@AA=6000@ASlevel@AA=29@ASicon@AA=avanew@AASface@AAS201711@AAS22@AAS00@AAS2a8e316bd4521e7aeba18c76814e7552@ASpg@AA=1@ASrg@AA=1@ASne@AA=1@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=16697403@AScrk@AA=10@ASlrk@AA=9@ASrs@AA=-1@ASnickname@AA=比目鱼儿@ASgold@AA=6000@ASlevel@AA=33@ASicon@AA=avanew@AASface@AAS201712@AAS04@AAS14@AAScda2878c9736e6f08641e9545c0f79fe@ASpg@AA=1@ASrg@AA=1@ASne@AA=1@ASih@AA=0@ASsahf@AA=0@AS@S/list_all@=uid@AA=153885927@AScrk@AA=1@ASlrk@AA=2@ASrs@AA=0@ASnickname@AA=汪可呦@ASgold@AA=1937587500@ASlevel@AA=98@ASicon@AA=avanew@AASface@AAS201712@AAS09@AAS21@AAS5e073dccfa54769769e1e5054c474797@ASpg@AA=1@ASrg@AA=4@ASne@AA=6@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=5640255@AScrk@AA=2@ASlrk@AA=1@ASrs@AA=0@ASnickname@AA=黑粉头子丶黑猫警长@ASgold@AA=649410000@ASlevel@AA=93@ASicon@AA=avanew@AASface@AAS201706@AAS22@AAS23@AAS8056b4d71d34b8092092305fe0c00650@ASpg@AA=1@ASrg@AA=4@ASne@AA=0@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=22844460@AScrk@AA=3@ASlrk@AA=4@ASrs@AA=0@ASnickname@AA=雨过丶天阴@ASgold@AA=379196800@ASlevel@AA=67@ASicon@AA=avanew@AASface@AAS201712@AAS09@AAS21@AAS6c2332ca48bc57b5d31017e55b9e67f7@ASpg@AA=1@ASrg@AA=4@ASne@AA=5@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=145316022@AScrk@AA=4@ASlrk@AA=3@ASrs@AA=0@ASnickname@AA=怡红霸主@ASgold@AA=367148000@ASlevel@AA=67@ASicon@AA=avanew@AASface@AAS201709@AAS24@AAS22@AAS58d3192f4f0e746cdc44647856217680@ASpg@AA=1@ASrg@AA=4@ASne@AA=0@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=100619363@AScrk@AA=5@ASlrk@AA=0@ASrs@AA=0@ASnickname@AA=神秘人@ASgold@AA=232225900@ASlevel@AA=0@ASicon@AA=avanew@AASface@AAS201701@AAS16@AAS17@AAS6ffeec5803dac53a54ebc24af8ca10c1@ASpg@AA=0@ASrg@AA=0@ASne@AA=0@ASih@AA=1@ASrl@AA=0cd1ba2dd341319277e69ad93615a2c1b0c9739c13dce9e5a4d9fd5217660366cba4557d6751af2012df4f7156ca7ca7562d931a415f05e49a477efc626e8ce36dd0ba90735b7ecc3904f2b87d782ce41c230deec3ed038eae7e60f3c015543738ccf0af59c03454a0202f4ce27b959f2a2f708e397a37a9a099c628249095c2f0b7d3028d6f8ef74199163f99f76e7b6c231c294aa9e3e8ea2d52aa15dd00ce0e16f11e1f5e653603e965081749e4369215245a64f484c25bc2e27dbeb40acd8deeec2bccf93736@ASsahf@AA=0@AS@Suid@AA=49895392@AScrk@AA=6@ASlrk@AA=7@ASrs@AA=0@ASnickname@AA=哇塞丶悟空耶@ASgold@AA=214358000@ASlevel@AA=61@ASicon@AA=avanew@AASface@AAS201712@AAS02@AAS20@AAS8619c13d24c9baeb28e433350d4375ed@ASpg@AA=1@ASrg@AA=4@ASne@AA=5@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=536351@AScrk@AA=7@ASlrk@AA=8@ASrs@AA=0@ASnickname@AA=隔壁村八大爷@ASgold@AA=151671300@ASlevel@AA=120@ASicon@AA=avanew@AASface@AAS201711@AAS30@AAS21@AASfe3ef304fe43054b78005acc5c7d1e15@ASpg@AA=1@ASrg@AA=4@ASne@AA=6@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=163947375@AScrk@AA=8@ASlrk@AA=7@ASrs@AA=0@ASnickname@AA=你是我的大头吗@ASgold@AA=151298200@ASlevel@AA=55@ASicon@AA=avanew@AASface@AAS201710@AAS26@AAS15@AASbf9c12228ea3909ae9a8885d546905e9@ASpg@AA=1@ASrg@AA=4@ASne@AA=5@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=9960990@AScrk@AA=9@ASlrk@AA=8@ASrs@AA=0@ASnickname@AA=豆子的老年粉儿@ASgold@AA=146341800@ASlevel@AA=55@ASicon@AA=avanew@AASface@AAS201712@AAS09@AAS19@AASe5b558a8725f198995d933c0560a5e00@ASpg@AA=1@ASrg@AA=4@ASne@AA=4@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=14855204@AScrk@AA=10@ASlrk@AA=11@ASrs@AA=0@ASnickname@AA=莹 莹丶女主播@ASgold@AA=122009000@ASlevel@AA=117@ASicon@AA=avanew@AASface@AAS201711@AAS24@AAS19@AAS907aa6cf6921e80a9b4b19fabba082de@ASpg@AA=1@ASrg@AA=1@ASne@AA=6@ASih@AA=0@ASsahf@AA=0@AS@S/list_day@=uid@AA=536351@AScrk@AA=1@ASlrk@AA=2@ASrs@AA=1@ASnickname@AA=隔壁村八大爷@ASgold@AA=200000@ASlevel@AA=120@ASicon@AA=avanew@AASface@AAS201711@AAS30@AAS21@AASfe3ef304fe43054b78005acc5c7d1e15@ASpg@AA=1@ASrg@AA=4@ASne@AA=6@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=9853292@AScrk@AA=2@ASlrk@AA=1@ASrs@AA=-1@ASnickname@AA=TinyTank丶@ASgold@AA=140000@ASlevel@AA=42@ASicon@AA=avanew@AASface@AAS201712@AAS16@AAS02@AASf6bcede14e11946f07908e1e02694b44@ASpg@AA=1@ASrg@AA=4@ASne@AA=3@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=47007789@AScrk@AA=3@ASlrk@AA=32767@ASrs@AA=1@ASnickname@AA=咯咯哟Zzz@ASgold@AA=54300@ASlevel@AA=46@ASicon@AA=avatar@AASface@AAS201605@AAS21@AAS373f25ce444df6803098c157d57e30f9@ASpg@AA=1@ASrg@AA=4@ASne@AA=3@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=9744044@AScrk@AA=4@ASlrk@AA=6@ASrs@AA=1@ASnickname@AA=带带小坦克丶Nick@ASgold@AA=23600@ASlevel@AA=46@ASicon@AA=avanew@AASface@AAS201712@AAS01@AAS22@AAS40366ac8bcf092bd3a95e47d48f85ecc@ASpg@AA=1@ASrg@AA=4@ASne@AA=1@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=29996122@AScrk@AA=5@ASlrk@AA=4@ASrs@AA=-1@ASnickname@AA=惗惗卜忘@ASgold@AA=10000@ASlevel@AA=29@ASicon@AA=avatar@AAS029@AAS99@AAS61@AAS22_avatar@ASpg@AA=1@ASrg@AA=1@ASne@AA=1@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=155412194@AScrk@AA=6@ASlrk@AA=5@ASrs@AA=-1@ASnickname@AA=乜柒都唔洗讲@ASgold@AA=10000@ASlevel@AA=23@ASicon@AA=avanew@AASface@AAS201712@AAS04@AAS14@AAS10d7dbfa63134cb638a2c4e9ab6ef3fb@ASpg@AA=1@ASrg@AA=1@ASne@AA=7@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=113969068@AScrk@AA=7@ASlrk@AA=11@ASrs@AA=1@ASnickname@AA=骨灰盒007@ASgold@AA=6200@ASlevel@AA=9@ASicon@AA=avanew@AASface@AAS201709@AAS05@AAS00@AAS0bf2213c382a03c2d1e5301c0f05c34e@ASpg@AA=1@ASrg@AA=1@ASne@AA=0@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=178767330@AScrk@AA=8@ASlrk@AA=32767@ASrs@AA=1@ASnickname@AA=喜欢纳豆的小女孩@ASgold@AA=6000@ASlevel@AA=10@ASicon@AA=avanew@AASface@AAS201711@AAS25@AAS23@AAS64eea5c31ca1ce0813fbb845bb7dcb24@ASpg@AA=1@ASrg@AA=1@ASne@AA=0@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=16731996@AScrk@AA=9@ASlrk@AA=8@ASrs@AA=-1@ASnickname@AA=浅析头大的优势@ASgold@AA=6000@ASlevel@AA=29@ASicon@AA=avanew@AASface@AAS201711@AAS22@AAS00@AAS2a8e316bd4521e7aeba18c76814e7552@ASpg@AA=1@ASrg@AA=1@ASne@AA=1@ASih@AA=0@ASsahf@AA=0@AS@Suid@AA=16697403@AScrk@AA=10@ASlrk@AA=9@ASrs@AA=-1@ASnickname@AA=比目鱼儿@ASgold@AA=6000@ASlevel@AA=33@ASicon@AA=avanew@AASface@AAS201712@AAS04@AAS14@AAScda2878c9736e6f08641e9545c0f79fe@ASpg@AA=1@ASrg@AA=1@ASne@AA=1@ASih@AA=0@ASsahf@AA=0@AS@S/timestamp@=1513582896021/messageId@=26/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_ssd(){
		
		String template = "/type@=ssd/rid@=1/gid@=-9999/sdid@=1/trid@=1/content@=test/clitp@=1/url@=test_url/jmptp@=1/";
		String sample = "/type@=ssd/sdid@=5713/content@=新游中心每日一款好游戏，复刻FC时代《足球小将》手游，梦回南葛找回童年的大空翼！/rid@=2020877/gid@=0/url@=/trid@=643818/clitp@=7/jmptp@=1/timestamp@=1513584690207/messageId@=294/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_spbc(){
		
		String template = "/type@=spbc/rid@=1/gid@=1/gfid@=1/sn@=name/dn@=name/gn@=1/gc@=1/gb@=1/es@=1/gfid@=1/eid@=1/";
		String sample = "/type@=spbc/sn@=国服第一搬砖哥/dn@=雪傲娇丶/gn@=火箭/gc@=1/drid@=3416569/gs@=5/gb@=0/es@=1/gfid@=196/eid@=143/bgl@=3/ifs@=0/rid@=2020877/gid@=0/bid@=30088_1513584681_1543/sid@=58570383/cl2@=0/timestamp@=1513584680241/messageId@=290/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_ggbb(){
		
		String template = "/type@=ggbb/rid@=1/gid@=1/gt@=1/sl@=100/sid@=123/did@=321/snk@=name1/dnk@=name2/rpt@=1/";
		String sample1 = "/type@=ggbb/rid@=2020877/gid@=0/gt@=0/sl@=130/sid@=107697091/did@=179663703/snk@=透明元素水晶豆/dnk@=EyytUAHw/rpt@=0/timestamp@=1513606903030/messageId@=62383/";
		String sample2 = "/timestamp@=1513606907034/messageId@=62469/type@=ggbb/rid@=2020877/gid@=0/gt@=0/sl@=131/sid@=107697091/did@=97803784/snk@=透明元素水晶豆/dnk@=Fantomas光着腚/rpt@=0/";
		testForAllFieldsExist(template, sample1);
		testForAllFieldsExist(template, sample2);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_rankup(){

		String template = "/type@=rankup/uid@=1/rn@=3/rid@=1/rkt@=1/gid@=-9999/rt@=0/nk@=test/sz@=3/drid@=1/bt@=1/";
		String sample = "/type@=rankup/rid@=2020877/gid@=0/drid@=2020877/rt@=0/bt@=1/sz@=3/uid@=164744705/nk@=Koala哥/rkt@=1/rn@=8/flag@=1/timestamp@=1513608947031/messageId@=104647/";
		testForAllFieldsExist(template, sample);
	}
	//@Test
	public void isBrokenMessageIfMissingFieldsFor_al(){

		String template = "/type@=al/rid@=10111/gid@=-9999/aid@=3044114/";
		String sample = "";
		testForAllFieldsExist(template, sample);
	}
	//@Test
	public void isBrokenMessageIfMissingFieldsFor_ab(){

		String template = "/type@=ab/rid@=10111/gid@=-9999/aid@=3044114/";
		String sample = "";
		testForAllFieldsExist(template, sample);
	}
	//@Test
	public void isBrokenMessageIfMissingFieldsFor_ruclp(){

		String template = "/type@=ruclp/rid@=10111/gid@=-9999/ui_list@=点赞用户信息列表/";
		String sample = "";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_gpbc(){
		String template = "/type@=gpbc/rid@=1/cnt@=1/sid@=10000/did@=20001/rpt@=1/snk@=test1/dnk@=test2/pnm@=prop_name/";
		String sample1 = "/type@=gpbc/cnt@=2/sid@=9853292/did@=99998299/rpt@=0/snk@=TinyTank丶/dnk@=LH偉丶devil/pnm@=稳/rid@=2020877/timestamp@=1513608947031/messageId@=104650/";
		String sample2 = "/type@=gpbc/cnt@=1/sid@=136205801/did@=15459592/rpt@=0/snk@=豆子的柠檬茶/dnk@=没错是小丝/pnm@=赞/rid@=2020877/timestamp@=1513605815031/messageId@=44512/";
		testForAllFieldsExist(template, sample1);
		testForAllFieldsExist(template, sample2);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_online_noble_list(){
		
		String template = "/type@=online_noble_list/rid@=1/num@=3/nl@=贵族列表结构/";
		String sample = "/type@=online_noble_list/num@=41/rid@=2020877/nl@=uid@AA=53020866@ASnn@AA=豆子赐名的碧瑶你真非@ASicon@AA=avanew@AASface@AAS201710@AAS25@AAS18@AAS52ce8f1e08f9ef33e40c21aab51b2610@ASne@AA=3@ASlv@AA=50@ASrk@AA=33@ASpg@AA=1@ASrg@AA=4@ASsahf@AA=0@AS@Suid@AA=9853292@ASnn@AA=TinyTank丶@ASicon@AA=avanew@AASface@AAS201712@AAS16@AAS02@AASf6bcede14e11946f07908e1e02694b44@ASne@AA=3@ASlv@AA=42@ASrk@AA=33@ASpg@AA=1@ASrg@AA=4@ASsahf@AA=0@AS@Suid@AA=150354105@ASnn@AA=爱看豆子笑丶乌龟@ASicon@AA=avanew@AASface@AAS201712@AAS15@AAS23@AAS4019f0f70780fa95c6bfaefdc5cd2f96@ASne@AA=3@ASlv@AA=37@ASrk@AA=33@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=11816547@ASnn@AA=丶小時候@ASicon@AA=avanew@AASface@AAS201712@AAS08@AAS22@AAS2b31274172d4557365ca3a099c1fbd99@ASne@AA=3@ASlv@AA=32@ASrk@AA=33@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=73197179@ASnn@AA=这幅风景@ASicon@AA=avanew@AASface@AAS201712@AAS02@AAS06@AAS6f389757b9b1fe3cdf16872a3a8e7569@ASne@AA=2@ASlv@AA=37@ASrk@AA=22@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=24506856@ASnn@AA=豆子的履带清洗工@ASicon@AA=avanew@AASface@AAS201710@AAS29@AAS23@AAS0b455e5446d4d273a0402325e1cbe091@ASne@AA=2@ASlv@AA=26@ASrk@AA=22@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=33934234@ASnn@AA=丿Da1丶@ASicon@AA=avanew@AASface@AAS201711@AAS11@AAS23@AASfb4842ad12231f50af9bfb1093c3505a@ASne@AA=1@ASlv@AA=36@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=26457693@ASnn@AA=豆家的无冥啊@ASicon@AA=avanew@AASface@AAS201712@AAS10@AAS20@AAS71e896356426fdd7dc13d919bc7ff9e8@ASne@AA=1@ASlv@AA=34@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=56848765@ASnn@AA=水光潋滟不修仙@ASicon@AA=avanew@AASface@AAS201711@AAS15@AAS12@AASae6e3a0af3615e647dfd5ad5d1d0dc95@ASne@AA=1@ASlv@AA=34@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=18934615@ASnn@AA=豆国丶人马座的喀戎@ASicon@AA=avanew@AASface@AAS201711@AAS19@AAS15@AASef19cfee507c594ada2df623f7cd9d71@ASne@AA=1@ASlv@AA=29@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=57677323@ASnn@AA=o花啦一下o@ASicon@AA=avanew@AASface@AAS201712@AAS05@AAS22@AASbcc5f7c498078ff3f843fdf75ca80677@ASne@AA=1@ASlv@AA=29@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=30435125@ASnn@AA=青鸢Zzz@ASicon@AA=avanew@AASface@AAS201712@AAS08@AAS19@AAS475930d5a728fb0f610d843b709744f7@ASne@AA=1@ASlv@AA=25@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=153866725@ASnn@AA=允小忆灬丶@ASicon@AA=avanew@AASface@AAS201708@AAS26@AAS16@AASd3712291e4bebd10ffd7cf156c2b0a65@ASne@AA=1@ASlv@AA=25@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=44704327@ASnn@AA=零五零七丶@ASicon@AA=avanew@AASface@AAS201711@AAS21@AAS21@AAS65964867813fe00227cc0072da77c6a4@ASne@AA=1@ASlv@AA=24@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=663216@ASnn@AA=魈魍小草丶丶@ASicon@AA=avanew@AASface@AAS201711@AAS19@AAS19@AAS4c7dbb096fe28c0d421737d48315e686@ASne@AA=1@ASlv@AA=23@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=176534668@ASnn@AA=豆几丶lilDogg@ASicon@AA=avanew@AASface@AAS201711@AAS22@AAS00@AAS06cab8afbef0a6830e39a20d2486b35c@ASne@AA=1@ASlv@AA=17@ASrk@AA=11@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=3303532@ASnn@AA=下雨天2020877@ASicon@AA=avanew@AASface@AAS201712@AAS17@AAS16@AAS79e9ecaa68342db26a5b61779777f31c@ASne@AA=7@ASlv@AA=34@ASrk@AA=7@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=6618417@ASnn@AA=斜揽殘箫@ASicon@AA=avanew@AASface@AAS201712@AAS13@AAS19@AAS2504aa11dd52ba2ebf6b995824565098@ASne@AA=7@ASlv@AA=30@ASrk@AA=7@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=144393163@ASnn@AA=加油豆子你是最胖的@ASicon@AA=avanew@AASface@AAS201710@AAS15@AAS10@AAS5469f902a04647ed28162f7a19bacf9d@ASne@AA=7@ASlv@AA=30@ASrk@AA=7@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@Suid@AA=32631686@ASnn@AA=bwl269@ASicon@AA=avanew@AASface@AAS201712@AAS13@AAS18@AAS500d1302fe047e7155eb6e85ae680d72@ASne@AA=7@ASlv@AA=30@ASrk@AA=7@ASpg@AA=1@ASrg@AA=1@ASsahf@AA=0@AS@S/timestamp@=1513584668028/messageId@=289/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_upgrade(){

		
		String template = "/type@=upgrade/rid@=1/gid@=-9999/uid@=12001/nn@=test/level@=3/ic@=icon/";
		String sample = "/type@=upgrade/uid@=100791633/rid@=2020877/gid@=0/nn@=向来情深奈缘浅/level@=4/ic@=avanew@Sface@S201701@S07@S02@Sc629349b66442e9b18537b4c3c2f4076/sahf@=34580624/timestamp@=1513602928313/messageId@=881/";
		testForAllFieldsExist(template, sample);
	}
	//@Test
	public void isBrokenMessageIfMissingFieldsFor_ul_ranklist(){
		
		String template = "/type@=ul_ranklist/rid@=10111/gid@=-9999/ts@=1501920157/list_level@=等级榜用户列表/";
		String sample = "";
		testForAllFieldsExist(template, sample);
	}
	//@Test
	public void isBrokenMessageIfMissingFieldsFor_upbc(){
		
		String template = "/type@=upbc/rid@=1/gid@=-9999/lev@=20/pu@=0/";
		String sample = "";
		testForAllFieldsExist(template, sample);	
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_newblackres(){
		
		String template = "/type@=newblackres/rid@=1/gid@=-9999/ret@=0/otype@=2/sid@=10002/did@=10003/snic@=stest/dnic@=dtest/endtime@=1501920157/";
		String sample1 = "/type@=newblackres/ret@=0/rid@=2020877/gid@=0/otype@=1/sid@=10472674/did@=509232/snic@=十元的蛋堡/dnic@=时光抚平的哀叹/endtime@=1516196916/timestamp@=1513604916026/messageId@=26418/";
		String sample2 = "/timestamp@=1513608150024/messageId@=89329/type@=newblackres/ret@=0/rid@=2020877/gid@=0/otype@=1/sid@=9853292/did@=181267795/snic@=TinyTank丶/dnic@=N3v4z6A6/endtime@=1513694549/";
		testForAllFieldsExist(template, sample1);
		testForAllFieldsExist(template, sample2);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_blab(){
		
		String template = "/type@=blab/rid@=1/uid@=10002/nn@=test/lbl@=2/bl@=3/ba@=1/bnn@=ttt/";
		String sample = "/type@=blab/uid@=178767330/nn@=喜欢纳豆的小女孩/lbl@=7/bl@=8/ba@=1/bnn@=豆霸霸/rid@=2020877/timestamp@=1513582948028/messageId@=88/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_frank(){
		
		String template = "/type@=frank/rid@=10111/fc@=200/bnn@=test/ver@=1/list@=榜单结构/";
		String sample = "/type@=frank/fc@=17436/bnn@=豆霸霸/list@=uid@AA=136659391@ASnn@AA=ARURUA@ASic@AA=avanew@AASface@AAS201711@AAS12@AAS01@AAS3571c3fbd02209831f55bfff98ef7bba@ASfim@AA=51405800@ASbl@AA=27@ASlev@AA=50@ASpg@AA=1@ASrg@AA=4@ASnl@AA=3@ASsahf@AA=0@AS@Suid@AA=143492009@ASnn@AA=竹轮@ASic@AA=avanew@AASface@AAS201710@AAS21@AAS22@AASc7326e1b9187f2091535fb49a3473850@ASfim@AA=44540000@ASbl@AA=26@ASlev@AA=45@ASpg@AA=1@ASrg@AA=4@ASnl@AA=3@ASsahf@AA=0@AS@Suid@AA=53020866@ASnn@AA=豆子赐名的碧瑶你真非@ASic@AA=avanew@AASface@AAS201710@AAS25@AAS18@AAS52ce8f1e08f9ef33e40c21aab51b2610@ASfim@AA=38267100@ASbl@AA=26@ASlev@AA=50@ASpg@AA=1@ASrg@AA=4@ASnl@AA=3@ASsahf@AA=0@AS@Suid@AA=143799282@ASnn@AA=初心若尘只为豆几@ASic@AA=avanew@AASface@AAS201710@AAS16@AAS22@AASa602a8848c7feb5c4ea3710935d21992@ASfim@AA=36260200@ASbl@AA=26@ASlev@AA=49@ASpg@AA=1@ASrg@AA=4@ASnl@AA=3@ASsahf@AA=0@AS@Suid@AA=9960990@ASnn@AA=豆子的老年粉儿@ASic@AA=avanew@AASface@AAS201712@AAS09@AAS19@AASe5b558a8725f198995d933c0560a5e00@ASfim@AA=32297700@ASbl@AA=25@ASlev@AA=55@ASpg@AA=1@ASrg@AA=4@ASnl@AA=4@ASsahf@AA=0@AS@Suid@AA=152770100@ASnn@AA=心心咫涯@ASic@AA=avanew@AASface@AAS201711@AAS15@AAS23@AASac96f92422796549f11b00e3d730979e@ASfim@AA=32113900@ASbl@AA=25@ASlev@AA=53@ASpg@AA=1@ASrg@AA=4@ASnl@AA=4@ASsahf@AA=0@AS@Suid@AA=136700171@ASnn@AA=老贾贾斯汀@ASic@AA=avanew@AASface@AAS201706@AAS08@AAS07@AAS6bf7707d7bbde9507974e1daa04f8073@ASfim@AA=31716600@ASbl@AA=25@ASlev@AA=45@ASpg@AA=1@ASrg@AA=4@ASsahf@AA=0@AS@Suid@AA=9744044@ASnn@AA=带带小坦克丶Nick@ASic@AA=avanew@AASface@AAS201712@AAS01@AAS22@AAS40366ac8bcf092bd3a95e47d48f85ecc@ASfim@AA=30246900@ASbl@AA=25@ASlev@AA=46@ASpg@AA=1@ASrg@AA=4@ASnl@AA=1@ASsahf@AA=0@AS@Suid@AA=153885927@ASnn@AA=汪可呦@ASic@AA=avanew@AASface@AAS201712@AAS09@AAS21@AAS5e073dccfa54769769e1e5054c474797@ASfim@AA=30189400@ASbl@AA=25@ASlev@AA=98@ASpg@AA=1@ASrg@AA=4@ASnl@AA=6@ASsahf@AA=0@AS@Suid@AA=23163537@ASnn@AA=狗头医师粥大叔@ASic@AA=avanew@AASface@AAS201710@AAS09@AAS00@AASff8690df12b81b1857851a6959a3f7b3@ASfim@AA=29450200@ASbl@AA=25@ASlev@AA=51@ASpg@AA=1@ASrg@AA=4@ASnl@AA=4@ASsahf@AA=0@AS@S/ver@=413126/rid@=2020877/timestamp@=1513584846667/messageId@=339/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_srres(){
		
		String template = "/type@=srres/rid@=1/gid@=-9999/uid@=12001/nickname@=test/exp@=3/";
		String sample = "/type@=srres/rid@=2020877/gid@=0/uid@=33421437/nickname@=廖小敏先生啦/exp@=0/timestamp@=1513605614033/messageId@=37858/";
		testForAllFieldsExist(template, sample);
	}
	@Test
	public void isBrokenMessageIfMissingFieldsFor_rri(){
		
		String template = "/uid@=2020877/rid@=2020877/cate_id@=2/rid@=2020877/ri@=sc@A=519000@Sidx@A=109@S/type@=rri";
		String sample = "/uid@=2020877/rid@=2020877/cate_id@=2/rid@=2020877/ri@=sc@A=519000@Sidx@A=109@S/type@=rri/timestamp@=1513584946144/messageId@=381/";
		testForAllFieldsExist(template, sample);
	}
	
}
