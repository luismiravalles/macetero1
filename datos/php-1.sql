-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 10-10-2024 a las 18:13:57
-- Versión del servidor: 5.5.57-0+deb8u1
-- Versión de PHP: 5.6.38-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `php`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actuador`
--

CREATE TABLE IF NOT EXISTS `actuador` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `estado` varchar(3) NOT NULL,
  `hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `consultado` timestamp NULL DEFAULT NULL,
  `activo` int(1) NOT NULL,
  `estadoSolicitado` varchar(3) DEFAULT NULL,
  `horaSolicitado` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `actuador`
--

INSERT INTO `actuador` (`id`, `nombre`, `estado`, `hora`, `consultado`, `activo`, `estadoSolicitado`, `horaSolicitado`) VALUES
(1234, 'Estufa Lamuño', 'OFF', '2024-09-13 22:16:57', '2024-10-10 04:34:22', 1, NULL, NULL),
(2234, 'Pruebas', 'ON', '2019-12-10 19:16:02', '2019-12-12 16:26:57', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foto`
--

CREATE TABLE IF NOT EXISTS `foto` (
  `id` int(11) NOT NULL,
  `foto` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `foto`
--

INSERT INTO `foto` (`id`, `foto`) VALUES
(1234, 0xffd8ffe000104a46494600010200006400640000ffec00114475636b79000100040000001e0000ffee000e41646f62650064c000000001ffdb008400100b0b0b0c0b100c0c10170f0d0f171b141010141b1f17171717171f1e171a1a1a1a171e1e23252725231e2f2f33332f2f40404040404040404040404040404001110f0f1113111512121514111411141a141616141a261a1a1c1a1a2630231e1e1e1e23302b2e2727272e2b35353030353540403f404040404040404040404040ffc0001108007600a003012200021101031101ffc4008c0000020301010100000000000000000000040500010302060701000301010100000000000000000000000102030004051000010303030205010605040300000000010002031121043141125105617122321381b1d12333140691a1422434c1e152725373151100020201030402010500000000000000000111022131411261320304518122b1c1721333ffda000c03010002110311003f00f0c1b45ab6371d9400688b8f1d940ebdc7553762b00bf1bba1fe0a71701ba29ede152095186e2ae201de812f21d55b04fc41a157cdfe68e6343df40f690056a45140d639a5c785b6350872e83aa3002f7004d1536c7cd6d2f123906000e94590692e144c9e0469c9a02d028e5d168d50ef6b86eb489e40e0ebb7ec41add05594c3436ed18d83915825798e73ed36e2ef0bee98bbf6e31c3d2e6fd47dcbce55cd2083e2085e97b377913b463649a4c3d8fd9ffee827f20ba6b28124fdbb20a8600686f43f7ac1ff00b7e502b4703f43f62f48246f278a8a83715be8a177445e015bbdd267907f69c90683f982b3ff00e766b356d475057ae7b8917433d80ec95dc75c5ea9fd1e5dd04a05da7e97583cbda6971fc97a67c31d6b4a1434f8f1c8da114f1a2cae6b568f47079e739daf22b925c6b7256f23292b983405588aad71e8abc912e2db2345530887a5be481634fde8f86174af8e315b8d9230b3895b62b2636a023b3309d0465c6a85886891e11d3ebfe4118ddba5ca3460ad2e8b3fb6f39d52d6720353609a7608039a1c456f4d53c8d948e434d2a2e56559cc9d57b56b8497d9f359607b0d08f15c4556cad3e299e6b28f78ff0088207d52e228e6f9ad5b4e0e6f629c597911c6f2446472177350a438547451847ada7dc7da51631fe48039b67d2fe2a9da72a720d0ca41a3aed5bd0b5c1cc36d41087f8cb751521751ca5a6847a7a2165394356d18639c5cc323c3a5359b4e5ff2089f9c8e42fcabe93536499834734db6213eecd2e1657f6d9638cffd0fd9de1e6953d8b53868d1cb329c1d7738348da87d5f55672dfc5a4beaead1c08b0099e3f6b80e4ba322ada59693764c72c738541d9075dcb2f1f8e6040ecc7f2e25a2e6c56726551a49683434b234f6874b3b9ac75982f543771ed3361b39ba846b64141afebc0948e5213d4ad38d18e531d9f2494f35a4b1f06b8785533673d564ca3ba6fda1a0e5c7cac38254d003be89942df434f82339924d6069df18dfd238820dbaa4100a908898b89702491d2ab0c71eb012d9c9d5eaa83d6fedd6810b4dbdfba72da08a5f6ea52afdbb4fd38be8fe89b1700c9ae75d827af6a1bcdfe8fe8f0fdcda06464d340ea2501dc48778a75dcc7f71906f776e94fc25d400dc9dd4abdcc3ed6dfc4e03482e9dd0fe11682110c2d730168a03a054daba138fc4f36b3813b57aad238cb230d3a81754b441c55309a1120a8b3900e8cb49045c26a42c278b90e407d50ada3016a40e19cc674ab7709ac2d648d6c919f10424f246e8dd470a6eb4c5ca7e3481cdbb7fa9bd53bace50159ac33d9768ee3f8fc720fe271a35c774e249dbf1d4ef55e4639639d8d9633aff001056f3656498800e240d42497107578af595c871db5e26972df6a368d0b1fdc8698c3c6c91c33e5324263e401f75343e6b2cccbc991ae12b89f512014231059dd4bb4fd197686874ee24542ebb89689640dd28bbecb4a4c4f4086cf70f9a4a682c8b59392af2d945b47027a26708f437c9099b872e24e229083c854108d807e1b7c96276f940f30a4856107e73422323f34ac22b6433cca56757accf5ffb76a6034ad9e9949511cdeed520ecb952c513da185c39d41051393dcb243640c8896bae4d74299592aa2d6f15ade46d46c23eea0fc929d092954a5c18d22c51f9924cf27e4671ea7c50b1186a04fedd94ebdc4fdbfd2a51c77478e32b9baaf049bf44432ae8da4ea42c1b340fe71bdcf3003e81b511403788e3edd956c7054c48554a85db9403d2916a3bd01e4f8248fe391c011a1dc25b2c6627f126bd08dd4cdff21cb057ad63724ec178b94fc6903db769f734e857a2c6c88a7884911a83a8dc1e85792a9a14c8e4bb0725862b34c6d2f6ecea85ad4dc35bec7a1ab9a0b9beddc25b9aeac7212741628cc7c98e78be588d41d46e0f429666643099623623449052584763b3253e21019ceacf31eae2987683481e4754ab25d59243d49416a3ac23d7775edcdc9c6320fce8855a7c3a25b8df94df2527efb9c627805ad0450d06c82c0c97cb27c75a000927fd13f198212d236c93f8eb18ff00c967995a460644a1b2fa65afa4d6c51991db590f0c98dfa1a3987ef496a33a7d7f2d5427bb1ff6489871aa40ad775be4c4c1148388b91a2c3b24ad18c6b4b396b953b68ed2e42d0b8a3ae2cfc8e2443dc8010beb41ea3e6927074ac6b582a413609d77478730b7a9aa4a67920bc66953af929aee27edfec5fc558383039d2d28e653d88c68a46d075a5d2c832725d34843e8e37778a3c495682752ab74f079f5d48e5ac4cac554319146e796b780a0a592243b14e6ff0094f1e2b044e5b39c86400973b5e887a386a0ae8abc1169c9cec517dc7fc86ffeb67d88602bb2233cf2c8a8d38347f00880eb1326581e1f19b7f53762149e4f96732529c8d68b28cd02eec523d4aad073db00fd238d69526bf449e435e47cd36c270660577ab9252eb1aa4aacb1db8486521fc37f805af698401f2115a8bd765cb846f045280daa111063b000c04961db7a8d13d6f56c9da968929910717f2a340bb5db87744662c8c99a31e6244acb861ebe08497261c72e8bf31dad46c50f367c92cb1cad686be3b03bba9d53e16a22e538d86d1e54b8333e127d0ef5350f9bdc65e608362dad970278729a65c9ab236e85bad775b42ec303e485d56d38f276ca57a671a1e8f87dbfc572ee5ab17cb952492441c490e02ab98e364ce224340d3646e6e108087c92d0cba340adbaa59fa291cf2d06c3477814bc232c8793cdfdb67068dc28e37b9cd7d41b59680868a744398f2712b6e4c3b8ba80663eed8ec7ad9169bdc8ac6c684dd62cfd390e1232ae3a381bad3f4d99fd6e6315fe900f74ad59280ea00e764b0fa6bc761aabfd53c01cd80f9591df0c5ff93eaafe3c4a7e23b97d134f4071f861781042713e7f8c7371b38ec97e4c4c9321f5b936be88b6e647147f130fa05e8162eca82a5dc2a7726cb2d40d6009d8d346681b650c523454b4d3c91a73e31a05c3bb9b89b7a4795536a2a95b8663343b158dd0387da83c8c18e2638f2248f05ae3e7303097baa41b03615f0559331998ee2351b29b94f056651844f70343766ce4cb17271e370f949a795962ce3a0a7fd4ad800ddb878d2a10694c8bcdc41b7708b033181d0103200b52c0f9a4bc035d470a106e9b7c31b85db4aeedd52fc88cc538638d6375da4eb5f14cbe0c9a419db592cd13f1dac0e2eab872340075571e4c7144f865a70ad45af51d10b8f9391c9d103e915008e8b2c805ae1cb41754bbc24814ab49dba871cb3290e681c437d25e76f00b37643c8692582bb552b71bae4949c7a8797419bf288a8f91a0785d6126597347295ce3b80288254b7137209764b2f46935b5cae4e51b716345160a91e281c99a9c87914a01bae4caf3bae14461025965c4ea5528a91016a2aaa9558077c5c5ad20546e9e76f8a9dadcf245cba895e3c64c41e0d2e51ccc9922c47c563cab722f742d591938202e1ee151d6d5fb56d1dfd85cdf3155145331a30b79538907ab695fb50bddb901181522b671a035e9aa8a2d5ee46d81b109f9cf20569dc08a8b1a535b6aa289eda95aff009b17bab55cdd4511244baaba8a22022a51458c452ea28b188aaea28b008a5d451130c710ff006e2db95a503c863ddf1b1c40748454341372436a6ca28898ffd9),
(1235, 0xffd8ffe000104a46494600010200006400640000ffec00114475636b79000100040000001e0000ffee000e41646f62650064c000000001ffdb008400100b0b0b0c0b100c0c10170f0d0f171b141010141b1f17171717171f1e171a1a1a1a171e1e23252725231e2f2f33332f2f40404040404040404040404040404001110f0f1113111512121514111411141a141616141a261a1a1c1a1a2630231e1e1e1e23302b2e2727272e2b35353030353540403f404040404040404040404040ffc00011080076009c03012200021101031101ffc4008f0000010501010000000000000000000000000102030405060701010003010100000000000000000000000001020304051000010400040404040405020700000000010011020321120405314151136171223281911406a1b14252c1722343156282e1c23383b324161100020201030304020300000000000000000111021221310341516171813213f091224204ffda000c03010002110311003f009add313ee806f20a2b347111cd11f0644b59791949c070527f9026194c31eaadfc9159ab298a33162445b9a21a5b672318433118e014d9ea9392729e8ca6ab59dbcdeac3f152db09228d9a7b21ee833f8268aa6490204b710cb44eb292334b1f0e8923ac81993c23ccf55193ec217728c431f50600e2195dfae84602354000cd880827456023089e64a68d369644b59e40146d3dd12a56cc4a0d78cac939e8cac7773111ae0fd70e4a0fa120bc66149085b0243e3d555c12a492bbc138c406e4062add6d2c4803e015184a75c9cc4165646a339f690aacb22c9cf3f4c32e6fd2e3055b4f76aa3b91d25d92632662d16f92b5a69836443f159dabbeba778b0ce6619abca2438b958dacf34a7436ad26af435353769680f618400e659655fbe69e2e34f4f74fee90118fe3c553fa4eeda4dfdc364bd51912e08f25621b648f0aa368e4c7d5f250f93b165c755b95cef1abca224c22dd22a19ea35977394bf942d18e82f87b6bb23e06a331f3493d1da31953273ce27b47f155c9f9349aad954caeddb22c431e0d3397f347d1ea33fb62f97838667fdcb545048f598c074b6427f93287b34fd4f6fb9a66c8ef964deefe64d7b0cff206118a690ad4cd72248837928f2c39b85dd91e7415c84d6560c2900994f281ccaced46e9a1ae4635d9dd2398187cd3243165821232ab5eeda298c67965cc156617516635d919f91539210c1901c22c946b0f2f90509ba73c201bf12a96e5ad772f4e1bdf62c0b650f56666e6e97fc8da3da4cbcf82ac29ba45fb7391eace9e34da83fdb90f30cb9efcf3b248eba7f9aab5b5a493ebb504b921fc02496aef971994b1dbf572f6c63f3524768d7138c081d47a962eedeecdd2e35b24410d45b096784c890fd4e93bc7ba2d99ee49c12eaec763d54b8ca51f387f175247edfd41fd40fc44544aee4e7522d06e90d36bacd6d9446c9cf1807c21e4adddf75ee1224550aab0787a714d1b018fbfff0028097fc36923eeba31fe6989295785099935c7331253bb7fddedf76a4c4748e0a9d9acd5d85ecba723e256c7d06d90c65a8d3bbbfa83ff00140a3658f1bf4fd3d20a8cfd4b2b556d5460ca723ee912998677c7dbfc574425b34386a7c0646e1f249f55b4f75bea6c6c8cf87eef24c976633f0729fe577031396d8c6209893e2a096e7b84e32ffd9961ce213a50aa1066720961cdcf32aa4b4f3b266584412d8ae9ada4e1f103adbaeb089593b2c6e2e52d0059613554f67e981c412a3b3477c0fb730ea15adb681033d4599a13abd8dcc95376955b254a7b11eac591c8320171044c447354f2ce384818b7118ad2b676ddac88ac195920f003f775520a72d37cad9136c983729757514b68a7a8c5d9e8650bad05c4a43e255886edb8c180d44d8727ff0082b145b4d4202cac59284e32103fdc11e456a6e5a6d39d929d5f6635dfa9b6529b066e918f80477530d6e4a4fa37a19d4fdcbba54184a323d65172ac7ff5dbd12089462dca30c0f9a836a942cdcf47576e2c6e8465cdc02bd167a5d3463322980607f4854e4b52ad275992d456b2f91c09fbbb762ef637f28651ff00f4fbacb8ea6c03c0a65d58cd2c03397f9acfb226acc38c25c14d6b47fd48b3baea6e6835db8ee3a9ae896aac10b2594c9f10a0b6ed6f725016ca60120124b96517db61f77a0f42ff00815d66c11d1d82dcd54657b86910e4627154ba556f4e85aadb472533aafd44bfc5464ea48789cde44ae9779ba1a6df0b4418f6a04d7ca4a0d46babdb730a74d5cbeaa64cb307317e0c9572969b8b68f731231d411c594b0ab552f6c89f209759bb6ab2762b108571fd518fa8fc5749f67995ba0b6db0e694a7c4e3c13926b5ca10ab4ed073bf4fad3ce5f247d2eb73719f0e9e2bd0b28e83e41332ff005b80ff00a7d3fd4b1fb7c1a61e4f37ca2f91992d2072b8e18251a5acc88958c0702312a80bed819463260494ccf3278975d785bb9cb3e0d2fa32c48b0961805353a7b6383e691c4c47060b3ebd55d0ac825cbb07c52d57ccdb132911077907e4ab6ad9a69b45b245fd37f4b72a753271189e11e89b7d76c676ca44e5b244c21e65d43b84a71d544c661c078e5e0141dfd519124924f34aa709cad895689468edf4c6dd6e8e99c709d8337880bafdff0068bb70a2aa74d961da91273603a2e376ab6e86bb4d6d84c8c264812e802ed86f0439957c710c7158f2b6aca0db8eb955bee616dff69ee1a5dc74faab25030aac1390071c175da82d4db2ff004c8aa35ef15c9f340c4f449abdd74c34b73e61e838b618aced7b59a92ea98ec709aab4578b3b93f9aad3267510c41e2c425d65a44862dfa81f352e9ae338c8d96669189689e2baaaa2a998bd6cd1aff6fec9ada2faf5d6408aa559982d86215dd96bd5e9ee17d9215696738c9e5866049f6a9764d6e7dbeb81b652976679a2493c3f2597afb755753a6aa32cd2aa1134d310d2cb277961c5964dbb59a7e85f4ad54224fb86c8cf7e26044876a0010aaef643d4398edfe20aa16111d7c7b6718e5ce1dfd43dc31536e964e57c048b978e1cd82d6b58c7d0cad69928ea67c4362e5d76ff0067c5b6bf32eb81ba723390388c57a07db96428daaa12726518c9807e4a39fe1ee4f17cd9b899fdeffb7ff32a0373ef5f28d3202b80624877927fd5d9dd678be4f77fbba2e4867446a79bd31a65179919892e3e2ac0a2912112189e00e1c549b66e1a6a698d36696bb64e499c83cbaad0ff002da2d4e536d31959597879038315dd64e7a9c492296a3432d211ddacc5c3c492f12eabd57c8d993b70980589019688dee89572a65488c5ce132671c7c0acfae596764f06917880144349b274e84964602d174a38478f9792906a74c7db203c19954d46abbb9a323ea1861c194568ae222625f10e12a9c4581706a681ab8194f28883ea1e2af4f77a6a03b7aa1637518ac181aeb9126227896053ed344eb3dbabb73e2f99c78a5b8aadadcb2bb4a11b70dfa31f5462244f33c1476ef53b226243c6418821f059c27a6844138798446ea64f9567f55666187cb7eac96cbe9b18cea05830c3925ac69725928830b444e51c41eb8f251e783265d7185672feac3e0ac93d949556732cd4d06e11d3c3b35cfb50c8624d98fbb89012e9f71a74fabaefae5226a8880901c82cc86a0ca230c461825cfe091be84fd8cd3d2fd2ea6eb6fbb551aadb2c959eaa8127a63e29d5e8aabe9cf7c459a9893fd4cf970e5e959627e0943bf2646d8cbc16edd9a44e6aea9012e9213fccad3aee853453095b653755e89089ca3272c0735891910702de453bbb671ce49f12eab66defd0bd2e94e9b9b10d6ce1fd3aeef402640c86241fde9ff5472e6eec7b9d7f4b3ac7af576c03111983d46293eb6e76ca38bfc1f82ae25bed526440e5e181ea9ec4104624702130b21e439e1d1759ce2df3ce417f573c19242c9d7883e612b8971603c924a048f4b94024a6653788674b9a4713c426736f9a5f018a0013e67aa77701700710c133211c4329054c1f38c500cb240b088c029e2624361f92880ad8bb93d4258cb2e00023c4286812b40f0962a2b5db282ec904a5cb04173c4a40248cc888cbc79a33cfaa600027010f24802e727994a2c90e652815864a655be0147b0145b21cd3bbf26f14c32811c134ca3d3e0a2176049de3cca3ba1ddf922145b6168c72f89527d0866cc7b8dd304844ea52642118ab90220920714a91820181ddd3cc893d3c90c8402392864250100304bc10ec91d00a9527274005003a50522100fe8931258073c824e0812224f12c5402c55a3d45bcb28f156b4da4c873486697275053ae9c4659fa875e6ad0d6d326024c7c555c96504b7df551119c1cc790547eb67dcee60cdc3c1d49a8d2f7bd75d81fa3e0ab7d16a33330e1c52140972579605b881c08e0518f44215ca87c121f2421007c1084200c7a2304210024c508402e3c824c508402927a25793f04210039e61049e88420073cc2316e1821080929efe6fe902ff82bf9357f4fddcf0eebb767d59dbf73e5c8dfee42147e89e87fffd9),
(2234, 0xffd8ffe000104a46494600010200006400640000ffec00114475636b79000100040000001e0000ffee000e41646f62650064c000000001ffdb008400100b0b0b0c0b100c0c10170f0d0f171b141010141b1f17171717171f1e171a1a1a1a171e1e23252725231e2f2f33332f2f40404040404040404040404040404001110f0f1113111512121514111411141a141616141a261a1a1c1a1a2630231e1e1e1e23302b2e2727272e2b35353030353540403f404040404040404040404040ffc00011080076009c03012200021101031101ffc40089000002030101010000000000000000000003050002040106070101010101000000000000000000000000000102031000020103020305050409020701000000010203001104211231130541516171228191b13214a1c15215426272a22333533406d192e1c2d24373242516110101010002030101010000000000000000011131022141126113a1ffda000c03010002110311003f00f4c3a83aff003d34ed65f13dc6b60b1d0711c6b266629e5392a4582d88f3159f24bae5395246bd95af9959db0c9e2475dac0303c411715e5ba6c6b1753cd0a0017b5bdb4f24c8c9871da62448aa0b6be1497a792f9b96e789b37bf5a9992b52ed6bfa500992091a1626e76dad7f156b8abc791969208e555934b865f49b5edc2ae3e5a12bdf22fdc847db50161cfc49dda38e41cc536643a3022b4d8f95289ba0f4ccf919e094c3937bb6b6f5791a03e37f9274bf91865c23803a9b7c6a607f5c2a1b4617a4907f93e3eee5e6c2f8ce3424eab4de0c9c7c95dd04ab20f03ad065c8e8f853ddcc6166fd1917d2c3da29567f43cc2dcc0fcfb0b7a8d9ec3c7b6bd128705b71ba93e91dd56d2d4daaf1f80062f504fa8bc5b6e08716e234d7857a44b58952083adc51a6c582604488083c6e2f581ba3344dbb0a77c7bf15f993fdad57ad92ea5f31aaf52f589f272f1d88c9837a03fcc875fdd3ad1e0c88720131386b711da3cc5769da573b2c6806a5714f65749a0b0352f55bd76f4c1b1329f55571221b0b5c371ab4b3e3191a39a2b9536de38d79c6c69626dcaac87b591b70f7581aba6665873791643dcde96f73015cb63794fa4871e5c6922866037a9b2b765c521e9e2d95923f6455e6cc67c79a29632aee8c01074b91a503a35cc93820dd56306fe5577c24dd331f28a025f9e7f67efa329d2d4202d337ec7df5968ce4c0c49a35636bd86a75e3e235a1c58f958fcc5491a48800630cdcc035d6d7d69818d4c40edb9b2ea3c2810a959652a34b683876d54619d31b2414ccc60fa71001f8d8d21c4c4c287ae5f13fb768f72837ed1e35eae597b1903f7861f78d6bcde2344fd46278939713444aa5f76d1ae97a678a4adc249d19f95282a09bc7270f6371a32e7288ddf21392233666b865d7b6e2b2b027711db5238464c53e3c8485908048e3c0565a318e48e550f138753c0a9bd58f8d79b97a265e1373b0e72b623e53b4f1b6a381a2fe6bd6b018c79b02e4229b6f5f4b7fa1ab7ad43e2a08d6b1e474bc698ee2bb5ff001a9dada6bc455307ae60e6c8b0a968e76e1138b13dba1a61502930753c662f14bf511fe0987abd8eb46c6cae7dc3a726553668d88bfb2d5b909d770b6a7dd5e77a9c8af9f3ec3628402c3b0daacef67e93a4ede3830ead96f85866688a896e000dadc5f5b0a1fd7e47e5bf53e9e7fe1dbd9dfb7ca9364264e495124dbd501dbbb522f4cbeb93e9397b0f3b97caf0e1b6f7ad7f59a7f2edc1d6d1549625716650478eb44be97a9715cda63fa2503d0ee97ec0d75ff6b5c509b1f22225a1dacc789d50e9ec22989b572c2d442d1992c4bfc78d877e971ef5abc59504b2928c2c547c4d6d280d065c3824f9a304f78163efa69869cb9914157b02070aec390e1a412ea63b0ddde09eda50b1e6c1a63e4b28ec4907317edabc3d4332166fa8816712000b446dc0fe16ad4b19ca6b2498ee7d6761b71ecd6bcd411a439a238e412a47132ac8bc18034cd7a961191d5df92c5480b28da6f6e1dd48fa52328404598249a79bd6bd1393507d37b54c37026605435cfca74fd115d404c7e3dd55c50872ef212aa1b52353f28ac456cc8581e2237343f2fcc372dee34bad1e480bb395db20b9b8537b6bda38d567c51342c31e5590e842decda107855a68184a58a91762430f3efad8f31d371c47d5e26dbb4892616235fd2b57a4bd22c29669ba84524ae5d965956eda9dab70053dacf6e44bf6579d7c9c4c6cdcb5c86e59770109d6e45efa776b5e86f587270b0f27211678836fddb9871f4a33fdd524f46e310c48b2416c701f4dc5a2234f35341fa36ddb77e9e46ffedb50baa74e8fa5a45998733a2c8e63b5ec41037507f3cea1c9e573b4fc761bade74f9f31afbb99be1e91fa4f5983f9592990bd8b22ed6f78a03cbd4b1ffb9c17207178bd63dd5b88ea18c46d924db700abfac5afe35a9f3e58a565640c14f106c6d5af9fc63e89d3aa61b1dacc626fc3202bf1ad29344e2e8e1bc883f0adad93819036e4417b9b7a9437db5e74c58f1e5650c750b1894ed034b79566f56a5d38b8aeda942ae420bc590c3f55c071f6eb44873b283b24a11ca806e975bdfcef53033b570a826d6a5f1f5cc30db676681fba45207bf856e8b271e6178a4593f6581f8507248924b86008ee22f595ba6c00ee8c189fb0a1dbf670adf61d95c22830ac59c8088d964dbafab436eea14790f14bbf26178f5dc580debc00e2b7a6434bf9556d4d3141958b380629159aeba03ea1af756de6ccb2b0490db71d1bd438f8d2e970a09756404fe2e07df43387908774133a7ed7ac7dbad6a768632e2e71cdea4b2346b11591d084160481f37b69d1a4b85d3f271b295800ea5cc8cd7fc42c7434eb5a968e1ab2223491968c93ebb3a9b117471c38550d6885a1daa1eeacbb9b70eeb30e1edabd59bc14ff0090e23cbd3634c5433b2ca5b63581f96c6bc972df9dc8fa59b9dfd3b1b79d7d12678fe9d9c11358ebb85ad7b560e763f33f96dbadf2eef4ff00ad6bf535b8c9325c0248b816be9c68b2cdfc475740ca06b71e353762c9f2928c48b03446447772aeb76054adc77f9d06473885d472d90dc6aa6e38f71a4647fece493fd435e827c49d994a00d622fc3bfc69035f9d39ef90d4edc2f5e56eca0aff0039ade146eca1a6b31f6561a3c38b1491859115c11c18035866ff001ee9cfea58794ff8a2250fd94cd0fa07955ae28111e91d42037c4cd7b0e0928de3df5419dd4e1cb38994913109bc3a122e2feda7ddb497a88ffeadc7f440fb69077f32453fc58a48ff005adbd7debafd9478b2f1e6b72e406fe36ac6380ae61e3e3644fcac91fc1766ddd9d971a8f1a0677b1ae83423d0d945f0335d076231122fef6b436c6ebd0718e2c903b5098dbdc6e298354606f1e54522f4bf1f39c65263e4e3cb8f2c9709b802a6dc6cc2989614036144846df58e363f7d55985ab6e22a9c74b8bdc9bfbeac66f0019c08e4791436805b85fdd58b99d3f99cce549bad6d97f4fbe996540396fcb5bdd780a4fb1b99b769bf75b5ad219f2ad32dd48f50ae4d083248781b5fdb7a50b8d345fdbe5cf1dbb0bee1fbd4632f5688d84e938238489627dab53ea35f2d394d34113347230201b5a9446494667d599ae4d6b97373244293e12b03a168a5b1b793d654c8e429478255526e37286fb549a5b2c24b2adfa37efa1c62f3799028c99984e42b9507b2fe9f8d5d205672626bf022dad654e171e40058e950c728ecac90e4f53dc55901b027d40af0f1aba753976bb4911511d8b00413a9b680da9f34d82fac1d4528ce37ea4c4f6443e34d97ab61b7ce4a1fd652294f509a09ba833c0e1d7962e57517bd32c36063e535dc1179c799ae03e9ab60e990be669e834e51b70f757374e9f23b2fb68ca0115d2971c6a05d9924b2750e9e2437b192c7d82b69eeac9962dd43049ece67c056cb822f54548068b0750862231e426fa906de1ba847b6ad1c485d5c10ae7bc78355ea94c239125b3a1baf0a2d978da97b493c50164daefbb4eeb5877503f389f772b93fc4fdad3e15ac659bb2a16248bf60ac8bd4f0985b9bb4f73023e228c99303db648ade44572c74106b56ae020ea2ba6d41cd91b5f7a2b0ee600d01ba7613ebca0a7bd0953fba451fcaac2a8cab8462fe4656445e0242c3dcd7aeb7e68a08fa88e756d08963d4dbbcad68b5400536a6466326484d9261ab006fba192c7dce2b0c4c98d9123b41288e4b93b96e413c755d29c11ae95c37abf54c85cb99d3dfd3b8a378e9f1ad7d371e29b24049030b9b01c68c628e425644571b49b3006b3b74cc07d79214f7a12a7f769e03bfa165f94915538f28f1a4cb89345fdbe6e4c3e1ccde3dcf7a28c8ebd17cb971cc3ba6880fb508a643caf9a1867e1861fd4b7baad34992a919c6884c4b8590336cda9dadc0deb34b3f53c9cbc779e0887289bbc6e6d622daab0bd6e5e171c2acb2597276fcbc7f821eea340849575717ed5bea347d7eda0920711ad4baeebb59bd26c0f0f95e9d794ecd4dce8e226450ec58d81d01165eeb560e643f537fa7fe25bf19dbc3badf7d77f3011e1493ae387dae8a225f4f1eda5df9f74efa9feca5fa8dbf2f33d16b5fe15bcb8c1a820f30678c7005b612135fc57dad4ba597a1ebcd820bf7c6dff4dea54a8d807ffcfb026213a30e0222e6e7c34a0e209c2302d396de7616df7d9d9c6a54acf6e16352459e3e5989ff00c8a9ff000aba375105832a35be5234bfef54a9595717273f849867cd6443f1228f1cb237cd0ba79943f06a952a02f9e95c3e552a505a3bee7b8fd135c1c2a54a0e1aeeba54a941d8efccf7fc28b1df966a54ab12a2f0d78f8d51e280b6e9262ac5186cda480363dcdc54a95aebca76e198c7b3a7cb69565532a58a065b0d343bc0af39a7e67c3fedff00cb52a575f4e6ffd9),
(2235, 0xffd8ffe000104a46494600010200006400640000ffec00114475636b79000100040000001e0000ffee000e41646f62650064c000000001ffdb008400100b0b0b0c0b100c0c10170f0d0f171b141010141b1f17171717171f1e171a1a1a1a171e1e23252725231e2f2f33332f2f40404040404040404040404040404001110f0f1113111512121514111411141a141616141a261a1a1c1a1a2630231e1e1e1e23302b2e2727272e2b35353030353540403f404040404040404040404040ffc00011080076009c03012200021101031101ffc4008f0000020301010000000000000000000000040500020301060101010101000000000000000000000000000102031000020103020305040706040603000000010203001104211231130541516122147181b13291c1d142b22306a15262723324a2c23415f0f18292432553731611010100020202020105000000000000000001113141022151120332617191c113ffda000c03010002110311003f00370ba95b25718e24b1cf2024bc8d72768b9f336b468cb8919818a44d49242ee07c7c97aa754287abe295372237bd5f7596fdd4c0bfaec5b296955037cbbfc97ffbad5aa3a380518303c2c41f852f931b0f2e18d32e1795412415e2a4f6e86b1fff003fd1e42dc89a7c764172a770b0bdafa81da6a6039d6b8c585b68bea2fd9a77d247e8d910233e375671b413b58defecd6b5c0c8ca93a7432cb3b195890cf606fa9ec22980def55b29eca123c89f7ec322c82cdf72c6e2ddcde34b97f52488409f0981bd8ed61f06029853b31a71da2f5849d3f1241e7894f8900d02bfa9707ff247344471052e3f61a3b0fa961e76f18ce5cc76de0a95b5f871a62a306e8d8963b01427f75997c7b0d633f4f9716169a2c99408c6ed85b703e1e6bd356171606de2282eb4cebd3a50a6dbac09ee04d337da97ff00bd4a9c71f79b71536fb682e9d2b264cb9198a59a65b16362c0dfb47ba8c1d3d0a8b1626c092a55bb2fc0edd7df556e9f28b7ce2e2e2e8751dfe4dd52f6ed86a4faff006318ba861381f9bb7c1811442bc4c3caeadec2290b62c8bdaa47b76fe202a8609469b0dfc35f856a7db7989fe5d78ec7d30242802f761f1ad42926ddfc2bcdefc8422cecb63c0dc6a28b87adf5284050e2455d00701b87d06b5fed3d25fa6fb9493318e56649933806426d61a0017ca34acec2ad22ca18b3a30b924d877fb2ab7f25ec6f7e1dbc2b3f2f395f8dc630f57990343d65095daadb8a788d807c456edf25005a67ea8b33ab8888b79cfca42777b68f56561a303efab6f96268574852d191b43682f7b779efa29155a460f1aff4f8807bd7ba84e94d2221e58dda2dfe9345a4e43b6f8c0216e4af68240b76d2141752f4e98cdf977d5002188d4b0038df81a59822f830af8b7c4d34eaf938ab872bcb1b300505d0fdedc369d6df7a96617f4117bb77e234eda23445db90bec6f8ad1d3c21a3f2bc6c491f780fbc3f7ad4038fcf5b1b1d753ed5a69362f3232b1cb1b1ba9b6eb1d181a4561998024866db12c8c55b6edb13720db852de810181a7465d8e1220ea7886035bfbe9c65e04d243308d0333ab85b11c4836a59d223689e547d1d522571c7cca2c7f6d2e833ac3371d32b19e173a358dc77837adab9604d88b8ed034ac8471fe9b865d8d1e5488f292351da18a6baf8529ff0070cdc795e219325e3664d589f94dbb6bdbe34110e4901c6d636bd88f9d8eba5794cee8bd40cf2cb1184abb96557b83626fdd5ab33a497da89fa87a92fcd2ac83f8d54fd95b0fd4258de6c585efc48bafdb5849fa7ba9ba8971f184b1100dd58037edd2e2847e993444acf198a406c51b88e159bd6c5cc374eb7d39c8e6e23a8ed08f71f4695a7aae813716962f6adedf45e917a37ecbfb8d672452c62eedb47017a9e572f43c9e92e0f2b3e31db67056bbfedb8f7ddeaa0b71ddcc15e6c063a975d83e6238569ca836df7fbfdd7ab83e57dbd81c380e9b00f669f0accf4e8780dc071b076fb68913444e8ea7d841ab020d450d1e34d07fa7c8923e17d4106dfcc2aeafd491f709c35c6df3c60e87f94ad6d7156b771a66a60bf363cec981f1e4e5b2b3ab12a190f91b70fdeaa431e4c002b44180bf995b5d4df83014cad6f1aa91a5334c401249373017c6716d095b3771ec6addfa8e39d19644208f9a36b5afdf6a2185c935cdaa69930172ba86098a6092057656e5fdd37b1b5a87e80d746dcd7731a16b9b9beb7bd323121e22ad8f0c6858aa804f1205aae45abb190240c7803723c2ac56b8a9b9c2daf7b8b7ba9104a15b23247214bdcb03a0bb78daf42e61cd42e63de52c08006e16b7758d1063946db036005fe9a1b2e7c9494ec9194696009adb2637f2af61dab7fa295e58c313c9ea02dd9f42549e0abda05726c8ca12dc4ee2eaa6d7045ca8ec606b313e45d8b32beed487456d7e8159b66aafc6ed6187d2651b97658e836bdb5fa684ea1d27059b1d35d8eec1acd7e0b7a284da59a085f5bb59596e7dcc6b397912b29931c6d42582ab794922de60eb56592ecb921cee990634b3c1082576a359f5d4ee36a1fd08dfcbe58f9afc7f878d319e4c39321c2068232554eef9415f14b9d7d9477a21cde77323d96bf336b5aff2f0db6ad66672c62e96ea71e173f1df1b19613f9818a802fa2f750de9e1f9b600c7891a7c28cea0c5e4859ad75320d001d89dd43dfcb58edb749a4c2c26cb8c013cb0940358dad7bf7def449e959cbaa67cba7ef2237d42add174561e0bf5d34dc2b2a52b85d695b4cc8d8773c56fc268583373a4815dcc7cc37bdd4dbca48d2cd4fd585ebcec03f2d7dadf88d06cb9b97bc218d093c2cc45cfbc51064ea097e66049a7ee323fd628317e7adb88e1f48a7626c9e3bd8fb81a780b8e748a017c4c95bf1fcbbfe126b7c2cc8a76911432ba58baba9522fc3e6a24e46503a3e9e207d941e3977ea99acfa9b45f869e01a5ad56c66be4c7d9ad50d51cbed3cbd1c0b834886e7e53efa1a5e9f0ce7712549b70358634938d82594b35c82028b1f31edad4f54c68d8ab92a57426c6df4815bc325b96a1321906a142804f828acb5adb3b4cb93ddf0141be408df698e43a7cca8587ecac5db6def55275ac4676391725d7bf746eb6ff000d5e39e29b589c3006c7c0d4aa4b971f325644b925eec07671a37764f2b97cc36b6db5bcbb78fb6f43c27fbb98936b1facd15ea20dfb798b7e16bd6f1e18e477509de6ca58d91544684f9476b7fca8620ecbdab69132e47e6830896d62407008f1f39ae819fb4e913586a0b30bfb3c86a5b2d59311af4858acfcc60ba2edb9b77d34e5c3fbf490acfb007c55623f76503f14756696c1dbd03a9752a423c6c05edc01dbdd4c4f6b9a7431d7b1abcd41a46a3baff135b4c4182d1c7951c8411b805363d846c96b18a448a255931e6b8bdc846ed37eea621e56ff00cc0ffc7114f63b11c69109f0c4aacfcc8d7b6e8ddfeca703a8f41d07aa45bf0ddb97e2054c51b1516e340c5e5ea395e3cad7dd460cce8e4855cd86e780e62fdb40a3c47a8e508e4591488ca9560c2c01eea628309ae02dbbc97dc01b5b8ded52f71ad72e5412a7cc01b1e1488283b8282cc6ec4137d3e63c74a1334e395657120d4f98007b7c6d5a1cc31c891317667d036ed3e6dbc2d4b7a975ac48b9b1cf89295462bb92402f66b5c7beba495813d43fd64bed1f015ae1468d15c8b9b9a1f35f7e4bb8160fb580f6a8344e07f4bde6b95dba7025618c701499d42e76601ffc8bf8453b149643ff00b0ccff00ec1f8455e08f3f9901766bdc0325ee0fdb5df4c399c4df77d5472c6b34ac8753c401df7a33d10f5f7e5b72afbb67dedb6efbd6b86791a0695cf0a03219f1f2d208b225914a967122a0e22eb62a3e9aeab64837e71b1ec2a847e1ac5963793035c0280866cb9954acc886daef4dc0fb3695ad0ffb803e59b1cfb51c7f9a80cb5738034313d487ca719bdee3ea343c59d93344b20440cd7046e36162578d8f75034636623b80f85509bf1d683f5d905c8689775ae2cf7bdb4b6a82ac327336ddb0defe0e87eb1404347135b7229f6806ad145120768d151890095005f8f750a3327fbd8730f66c3fe7ad7172c4e264e5bc6f1320657001f30247027ba80d63c3c6a724ca796842b3820371b564cdc28981d44cacc40507527d9566d2b37e913f32295251f96417dc092407dda6b493ad74eea1309b958d24972c46d17e2f7ecaf5eacae9752181bd88d7b6ae3e502ba4ed58c3ce6502b3b291a80a08f10a2ad8d9a902ec78dd85efb93691fb581ae669be64dfcdf55637ae5775d38311d53107159478ecfb09a5a4efc9c8945c2cafb96f71a580eda97ae8209a642f8114c92992d63f2df8def4fb9d17a6d966bf2edc34e35e7f1dcbcee0f053a519ef37efb9adf0c72e751dadd50b28b2ec5005efd86a036159cb3e14f921b1165408363a4a00b581b58826ae0e953b6d6695c7cdc1c2843e69b0603670d7bf8915a47d7bf4fe4738c664fede3334be4d36290a6c436bf352dcf8ba2b4108eaf24f128bf24e3806e6c376edc0f854e9d89fa44c59be9b2f282b6332cfcc41e58cba5d96c9c775ab5d7a4be6ae69c43d4fa24bb02990194809ba3700ee361d8682c6012203801bbf11a28b7424c3c4be4cbcac5b3472189bcc15b76be5efa05c4922aa27025b71ff00a8d4bd649e12510855b212c7ca48d7de29e2c18edc250dec20fc290c71442548dc7e51d1c9ee26c6f4a3a5f48c0c7f5714dd5b0ed970f250a3333292eac3cacabdddf52759d95ed8e2463ef114ba3411e6e6adef778bf01a070ba160623239ea303c98ede73bb6ea3b0824d8d5ba2c22089a2dcb26d080ba1dcadf36a1bb697ac9324a676e154cc87d4614f092a3701ab90abc7b4b695a1b93a57268c4d8d2c65d63dc00df21daa3f98d660e8c4cd5ea1812403fb546227daea14280f6f2dee7cd6e1424b99d5d4cfb6598324fb514a0da6227cb6f27b6b54e9196dfa8b1faa2e447e963401a25724b79594580f2ea5852d5c0fd48bd4b2d9a199f1e494b40fccd146e1c06fd34aeb8fd5932cc27d5cd7e3bcd1187d371b260124bbf79245d5caf0f0143e67fab97f9cd0b3751ea78aa171658d6326c159031bf126f5ce7e55aba381d1313b1e6b7f3dfe2294a298e7c98b71758e52aa5b520002b4ea9d63abe1643729e2686e802ba5dbcca2fa861db59a966c9cb63c4cedaf0ec157b4c44949b9ed034b20b7ce079b86b44fa89f97becbbf7edb785a85ca50f06c506ed3460d87f1d1fc9fef3937f27aad9bfb36dadbbd95784e578a3e9e99321599a5b93bc1428436b603520d6fbb0f6db6496efd3eda952a76db53451d75304c38e6592ca09da1830bfcb7d5037c287e9e313fb918854a720fa93b8df97bd2ffd441a6eb7654a957889c99e689c74d8f546c400f2c5cee2b7d6fe4144e2ae3185798cc1f5df6e17b9f0a952a76d426eb4098c27568dcb38d550822e6fa0bda914187fa67723faf7e6ee0dcb08f6df7b85bf2edc74a952af5d299660568dc5d234df2b4a7467dc58ee41ecefb56bd1446b0111b165016c48b5c79aa54a9795fe3fb346bf650dd5371e979416ca4a8b13c06be17a952b3361540bff00b9e9bb9d0b05c6e3bb7921077af6d023d61eb997bb9c22df2051e7dbc4ea3b2a54aefc565e9322fcf92fc77566c9d51913d348e90926db4295f76e06a54ae33f2ab7427a8275e0d23634971e5daa445dc3b5b5a062ddccc8ddf3f34eef6d85ea54ad77d2752c032cc89e9890799f997b5b65ff008abd17f63cafb9cde5ff008aff00454a9538397fffd9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotoplaya`
--

CREATE TABLE IF NOT EXISTS `fotoplaya` (
`id` bigint(20) unsigned NOT NULL,
  `foto` blob NOT NULL,
  `lugar` varchar(128) NOT NULL,
  `metros` int(11) NOT NULL,
  `autor` varchar(128) NOT NULL,
  `fecha` datetime NOT NULL,
  `verificada` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagen`
--

CREATE TABLE IF NOT EXISTS `imagen` (
  `id` int(11) NOT NULL,
  `imagen` blob NOT NULL,
  `hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lectura`
--

CREATE TABLE IF NOT EXISTS `lectura` (
  `idsensor` int(4) NOT NULL,
  `hora` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valor` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sensor`
--

CREATE TABLE IF NOT EXISTS `sensor` (
  `id` int(4) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `medida` varchar(4) NOT NULL,
  `activo` int(1) NOT NULL DEFAULT '1',
  `hora` timestamp NULL DEFAULT NULL,
  `valor` float NOT NULL,
  `suma` int(11) NOT NULL DEFAULT '0',
  `tipo` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `sensor`
--

INSERT INTO `sensor` (`id`, `nombre`, `medida`, `activo`, `hora`, `valor`, `suma`, `tipo`) VALUES
(1234, 'Lamuño', 'º', 1, '2024-10-10 04:35:00', 18, 0, 0),
(1235, 'Humedad', '%', 1, '2024-10-10 04:33:09', 81, 0, 1),
(2234, 'Temperatura de Prueba', 'º', 0, '2019-12-12 16:26:57', 24, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tema`
--

CREATE TABLE IF NOT EXISTS `tema` (
`id` bigint(20) unsigned NOT NULL,
  `titulo` varchar(128) NOT NULL,
  `comentarios` varchar(256) NOT NULL,
  `acordes` varchar(4096) NOT NULL,
  `fecha` datetime NOT NULL,
  `colaborador` varchar(128) NOT NULL,
  `estilo` varchar(128) NOT NULL,
  `autor` varchar(128) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tema`
--

INSERT INTO `tema` (`id`, `titulo`, `comentarios`, `acordes`, `fecha`, `colaborador`, `estilo`, `autor`) VALUES
(1, 'All of me', '', 'zoom:-4\r\ntransposicion:0\r\ntempo:163\r\nstyle:Stride\r\nrepeat:3\r\ntitulo:All of me\r\n8:E7;prefijo=;sufijo=;otros=;partInit=\r\n80:A7;prefijo=;sufijo=;otros=;partInit=\r\n120:CM;prefijo=;sufijo=;otros=;partInit=\r\n72:E7;prefijo=;sufijo=;otros=;partInit=\r\n48:D7;prefijo=;sufijo=;otros=;partInit=\r\n88:D-7;prefijo=;sufijo=;otros=;partInit=\r\n104:E-7;prefijo=;sufijo=;otros=;partInit=\r\n108:A7;prefijo=;sufijo=;otros=;partInit=\r\n56:D-7;prefijo=;sufijo=;otros=;partInit=\r\n0:CM;prefijo=;sufijo=;otros=;partInit=A\r\n116:G7;prefijo=;sufijo=;otros=;partInit=\r\n32:E7;prefijo=;sufijo=;otros=;partInit=\r\n112:D-7;prefijo=;sufijo=;otros=;partInit=\r\n24:D-7;prefijo=;sufijo=;otros=;partInit=\r\n40:A-7;prefijo=;sufijo=;otros=;partInit=\r\n96:FM;prefijo=;sufijo=;otros=;partInit=\r\n126:G7;prefijo=;sufijo=);otros=;partInit=\r\n60:G7;prefijo=;sufijo=;otros=;partInit=\r\n100:F-7;prefijo=;sufijo=;otros=;partInit=\r\n16:A7;prefijo=;sufijo=;otros=;partInit=\r\n64:CM;prefijo=;sufijo=;otros=;partInit=B\r\n124:D-7;prefijo=(;sufijo=;otros=;partInit=', '2016-11-13 00:00:00', 'luismiravalles@gmail.com', 'Jazz', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `usuario` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`usuario`, `password`) VALUES
('luis', 'domos');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `actuador`
--
ALTER TABLE `actuador`
 ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `foto`
--
ALTER TABLE `foto`
 ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `fotoplaya`
--
ALTER TABLE `fotoplaya`
 ADD UNIQUE KEY `id` (`id`);

--
-- Indices de la tabla `imagen`
--
ALTER TABLE `imagen`
 ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `lectura`
--
ALTER TABLE `lectura`
 ADD PRIMARY KEY (`idsensor`,`hora`), ADD KEY `hora` (`hora`), ADD KEY `idsensor` (`idsensor`);

--
-- Indices de la tabla `sensor`
--
ALTER TABLE `sensor`
 ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tema`
--
ALTER TABLE `tema`
 ADD UNIQUE KEY `id` (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
 ADD PRIMARY KEY (`usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `fotoplaya`
--
ALTER TABLE `fotoplaya`
MODIFY `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `tema`
--
ALTER TABLE `tema`
MODIFY `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;