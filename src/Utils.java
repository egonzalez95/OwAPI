import java.util.HashMap;

public class Utils {

    // Constructor (For initialization)
    public Utils() {
        this.initializeRanks();
    }

    private HashMap<String, Integer> ranks = new HashMap<>();

    // Utility that strips HTML markup from code.
    public String stripHTML(String code) {
        // in return: strip HTML markup
        return code.replaceAll("<[^>]*>", "");
    }

    // Utility that strips whitespace and new lines from a string.
    public String stripString(String text) {
        // in return: trip linebreaks / spaces / new lines
        return text.replaceAll("\\r| |\\n", "");
    }

    // Utility that fills whitespace in a URL.
    public String fillURL(String url) {
        // in return: trip linebreaks / spaces / new lines
        return url.replaceAll(" ", "%20");
    }

    // Utility that strips portrait/star links for ranks.
    public String stripRankURL(String text) {
        // Pre and Post links
        String pre = "background-image:url\\( ";
        String prelink = "https://d15f34w2p8l1cc\\.cloudfront\\.net/overwatch/";
        String postlink = "\\.png \\)";
        String post = "\\)";

        // run sanitation
        text = text.replaceAll(pre, "");
        text = text.replaceAll(prelink, "");
        text = text.replaceAll(postlink, "");
        text = text.replaceAll(post, "");
        text = this.stripHTML(text);
        text = this.stripString(text);

        return text;
    }

    public String rankify(String rank, String portrait, String stars) {

        // Strip links
        portrait = this.stripRankURL(portrait);
        stars = this.stripRankURL(stars);

        // calculate
        int rv = Integer.parseInt(rank) + this.ranks.get(portrait) + this.ranks.get(stars);

        return Integer.toString(rv);
    }

    public String determineCompetitiveRank(String imageURL) {
        if (imageURL.contains("Grandmaster")) {
            return "Grandmaster";
        } else if (imageURL.contains("Master")) {
            return "Master";
        } else if (imageURL.contains("Diamond")) {
            return "Diamond";
        } else if (imageURL.contains("Platinum")) {
            return "Platinum";
        } else if (imageURL.contains("Gold")) {
            return "Gold";
        } else if (imageURL.contains("Silver")) {
            return "Silver";
        } else {
            return "Bronze";
        }
    }

    // Assign all the ranks
    private void initializeRanks() {
        // Compiled with an XML crawler.
        // SYNTAX: image code, modifier.

        // Bronze
        this.ranks.put("1055f5ae3a84b7bd8afa9fcbd2baaf9a412c63e8fe5411025b3264db12927771", 0);
        this.ranks.put("69c2c1aff0db8429a980bad7db76a3388003e43f0034097dc4cfa7f13c5de7d7", 0);
        this.ranks.put("4d63c2aadf536e87c84bdb7157c7b688cffb286e17a5362d2fa5c5281f4fc2a2", 0);
        this.ranks.put("78ebb45dd26b0050404305fdc1cb9ddc311d2c7e62400fd6348a3a488c69eee7", 0);
        this.ranks.put("888c84f2dfd211cde0c595036574040ca96b1698578daab90ce6822d89f7fe0e", 0);
        this.ranks.put("3fdfdd16c34ab7cdc9b7be3c04197e900928b368285ce639c1d3e1c0619eea6d", 0);
        this.ranks.put("e8b7df4b88998380658d49d00e7bc483c740432ac417218e94fab4137bec4ae0", 0);
        this.ranks.put("45cc69ca29f3981fa085b5337d2303a4eb555853daae1c29351b7ba46b27bbcd", 0);
        this.ranks.put("8b4be1017beff0bcd1f7a48d8cdf7faf9f22c1ffd2bdeaaff2684da5cddeaa76", 0);
        this.ranks.put("1b00b8cab530e98c378de2f3e8834d92ee41b4cd7b118179a8ecbccee83c8104", 0);

        // Silver
        this.ranks.put("f5d80c8b7370cda9a491bdf89e02bcd8c6ba1708189d907c7e4f55a719030264", 600);
        this.ranks.put("ddb6f3f79241b8af2fa77b52910f60a2332db5d8347b3039d1328ae6d1272a59", 600);
        this.ranks.put("c59072a340e6187116f5ae7456674dd6e1cba4b15781922d63fb94f56d9539c0", 600);
        this.ranks.put("624461e537900ce98e3178d1a298cba4830c14f6a81a8b36319da6273bed255a", 600);
        this.ranks.put("ba68d2c0f1b55e1991161cb1f88f369b97311452564b200ea1da226eb493e2e8", 600);
        this.ranks.put("3c078f588353feeb3f52b0198fade12a78573a01c53050aca890969a395ff66a", 600);
        this.ranks.put("f9bc9c6bb95f07f4e882b9e003ba7fa5ca6552fb8e0c27473a8b031714670116", 600);
        this.ranks.put("8aa9f56cdd250579dd8b0ce6bd835934fffe8c27b9ce609f046c19a4a81591f8", 600);
        this.ranks.put("32f84a58719318fa0aeee530ed3240952ba9945b998cd9e8150ebb583db0d4f6", 600);
        this.ranks.put("c95fa44c02a1eae89a7c8d503026f181f1cc565da93d47c6254fab2c3d8793ef", 600);

        // Gold
        this.ranks.put("5ab5c29e0e1e33f338ae9afc37f51917b151016aef42d10d361baac3e0965df1", 1200);
        this.ranks.put("7fd73e680007054dbb8ac5ea8757a565858b9d7dba19f389228101bda18f36b0", 1200);
        this.ranks.put("0ada1b8721830853d3fbcfabf616e1841f2100279cff15b386093f69cc6c09ad", 1200);
        this.ranks.put("7095ee84fc0a3aaac172120ffe0daa0d9abca33112e878cd863cd925cd8404b6", 1200);
        this.ranks.put("fa410247dd3f5b7bf2eb1a65583f3b0a3c8800bcd6b512ab1c1c4d9dd81675ae", 1200);
        this.ranks.put("a938ef37b673a240c4ade00d5a95f330b1e1ba93da9f0d3754bdb8a77bbbd7a1", 1200);
        this.ranks.put("49afee29dc05547ceebe6c1f61a54f7105a0e1b7f2c8509ff2b4aeaf4d384c8e", 1200);
        this.ranks.put("2c1464fb96d38839281c0bdb6e1a0cd06769782a5130609c13f6ca76fa358bcf", 1200);
        this.ranks.put("98f6eea1a2a10576251d6c690c13d52aaac19b06811ed2b684b43e7a9318f622", 1200);
        this.ranks.put("6e1036eab98de41694d785e076c32dbabe66962d38325117436b31210b003ad4", 1200);

        // Platinum
        this.ranks.put("69fde7abebb0bb5aa870e62362e84984cae13e441aec931a5e2c9dc5d22a56dc", 1800);
        this.ranks.put("9c84055f9d91a297ccd1bac163c144e52bcce981dc385ff9e2957c5bd4433452", 1800);
        this.ranks.put("97c803711cddc691bc458ec83dec73c570b0cc07219632c274bb5c5534786984", 1800);
        this.ranks.put("c562ec882ababf2030e40ad3ce27e38176899f732166a1b335fd8f83735261f3", 1800);
        this.ranks.put("da2cb4ab3281329c367cea51f9438c3d20d29ee07f55fa65762481777663f7f9", 1800);
        this.ranks.put("460670e4d61b9bf0bcde6d93a52e50f01541177a20aaf69bbda91fe4353ed2b0", 1800);
        this.ranks.put("5a019024b384de73f4348ed981ae58ec458a7ae6db68e0c44cda4d7062521b04", 1800);
        this.ranks.put("1d5a458ecaf00fe0ef494b4159412d30a4b58ee76b9f0ff44b1db14ed211273c", 1800);
        this.ranks.put("f1d43d87bbe5868cb99062ac02099001dd9f8215831347d8978e895468e81ef6", 1800);
        this.ranks.put("27b2d05f97179aae72c8f72b69978777e1c5022f77e84f28e5943be8e9cd1d49", 1800);

        // Diamond
        this.ranks.put("5c83959aa079f9ed9fd633411289920568e616c5117b2a7bb280dd8c857f8406", 2400);
        this.ranks.put("ac14208753baf77110880020450fa4aa0121df0c344c32a2d20f77c18ba75db5", 2400);
        this.ranks.put("a42bcb3339e1b3c999fc2799b0787fd862e163ec504d7541fa3ea8893b83957a", 2400);
        this.ranks.put("7f1cc30ed6981974b6950666bb8236a6aa7b5a8579b14969394212dd7fa2951d", 2400);
        this.ranks.put("efe3ab1c85c6266199ac7539566d4c811b0ee17bc5fb3e3e7a48e9bc2473cf50", 2400);
        this.ranks.put("c7b9df20c91b10dc25bfdc847d069318ed9e8e69c5cad760803470caa9576e48", 2400);
        this.ranks.put("413bdc1e11f9b190ed2c6257a9f7ea021fd9fcef577d50efcf30a5ea8df989a4", 2400);
        this.ranks.put("625645c3c9af49eb315b504dba32137bb4081d348ec5b9750196b0ec0c9bb6a6", 2400);
        this.ranks.put("f9813603e19350bb6d458bbee3c8c2a177b6503e6ff54868e8d176fa424a0191", 2400);
        this.ranks.put("9e8600f97ea4a84d822d8b336f2b1dbfe7372fb9f2b6bf1d0336193567f6f943", 2400);

        // Bronze - stars
        this.ranks.put("8de2fe5d938256a5725abe4b3655ee5e9067b7a1f4d5ff637d974eb9c2e4a1ea", 100);
        this.ranks.put("755825d4a6768a22de17b48cfbe66ad85a54310ba5a8f8ab1e9c9a606b389354", 200);
        this.ranks.put("4a2c852a16043f613b7bfac33c8536dd9f9621a3d567174cb4ad9a80e3b13102", 300);
        this.ranks.put("bc80149bbd78d2f940984712485bce23ddaa6f2bd0edd1c0494464ef55251eef", 400);
        this.ranks.put("d35d380b7594b8f6af2d01040d80a5bfb6621553406c0905d4764bdc92a4ede8", 500);

        // Silver - stars
        this.ranks.put("426c754c76cd12e6aacd30293a67363571341eea37880df549d3e02015a588fe", 100);
        this.ranks.put("c137dd97008328ed94efc5a9ec446e024c9ac92fce89fa5b825c5b1d7ff8d807", 200);
        this.ranks.put("9a7c57aee22733a47c2b562000861d687d0423a74eb5e609c425f10db5528ed9", 300);
        this.ranks.put("b944cf1de6653b629c951fd14583069bc91b1f1b7efdb171203448b2dbc39917", 400);
        this.ranks.put("9b838b75065248ec14360723e4caf523239128ff8c13bda36cfd0b59ef501c1e", 500);

        // Gold - stars
        this.ranks.put("1858704e180db3578839aefdb83b89054f380fbb3d4c46b3ee12d34ed8af8712", 100);
        this.ranks.put("e8568b9f9f5cac7016955f57c7b192ccd70f7b38504c7849efa8b1e3f7a1b077", 200);
        this.ranks.put("a25388825a0e00c946a23f5dd74c5b63f77f564231e0fd01e42ff2d1c9f10d38", 300);
        this.ranks.put("cff520765f143c521b25ad19e560abde9a90eeae79890b14146a60753d7baff8", 400);
        this.ranks.put("35fd7b9b98f57389c43e5a8e7ca989ca593c9f530985adf4670845bb598e1a9d", 500);

        // Diamond - stars
        this.ranks.put("8033fa55e3de5e7655cd694340870da851cdef348d7dcb76411f3a9c2c93002c", 100);
        this.ranks.put("605c201cf3f0d24b318f643acb812084ff284e660f2bb5d62b487847d33fad29", 200);
        this.ranks.put("1c8c752d0f2757dc0bcc9e3db76f81c3802c874164a3b661475e1c7bd67c571f", 300);
        this.ranks.put("58b1323ab2eb1298fa6be649a8d4d7f0e623523bd01964ed8fefd5175d9073c0", 400);
        this.ranks.put("cd877430ccc400c10e24507dba972e24a4543edc05628045300f1349cf003f3a", 500);

        // None
        this.ranks.put("", 0);
    }


}
