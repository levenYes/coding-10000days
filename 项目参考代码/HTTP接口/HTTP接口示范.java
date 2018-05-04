public Map<String, Object> binding(Map<String, String> request) {
        String errMsg = "";
        int state = 1;//处理状态 1-成功 0-失败
        String code = "00000";   //状态码
        try {
            //1.参数获取
            String merchantNo = request.get(Constant.MERCHANT_NO);//商户号
            String userId = request.get(Constant.USER_ID); //商户用户id
            String actualName = request.get("actualName"); //姓名
            String idCard = request.get("idCard"); //身份证
            String bankCode = request.get("bankCode"); //银行卡编号
            String bankNo = request.get("bankNo"); //银行卡
            String mobile = request.get("mobile"); //手机号
            //2.渠道校验
            Channel channel = getChannel(merchantNo);
            if (channel == null) {
                logger.error("绑定银行卡["+merchantNo+"]渠道不存在");
                throw new BusinessException("10000","绑定银行卡渠道不存在");
            }
            //3.用户校验
            User user = getUser(userId,channel.getId());
            if(user == null){
                logger.error("绑定银行卡的用户["+userId+"]不存在");
                throw new BusinessException("10000","用户不存在");
            }
            //4.验证银行卡是否合法
            if(bankNo.length()!=19 && bankNo.length()!=16 && bankNo.length()!=18){
                logger.error("绑定的银行卡["+bankNo+"]不合法");
                throw new BusinessException("10000","绑定的银行卡不合法");
            }
            //5.是否绑定多张卡
            if(isExists(user.getId(),channel.getId(),bankNo)){
                logger.info("用户["+user.getMobile()+"]绑定了相同的卡");
            }else {
                if(countBinding(channel.getId(),user.getId()) >= 1/* && !isExists(bankNo)*/){
                    logger.error("用户["+userId+"]以绑定银行卡");
                    throw new BusinessException("10000","用户已绑定银行卡");
                }
                //6.验证银行
                Bank bank = getBankByBankCode(bankCode);
                if (bank == null) {
                    logger.error("银行编码[" + bankCode + "]不存在");
                    throw new BusinessException("10000", "绑定的银行不存在");
                }
                //6.鉴权接口验证
               String responseCode = AuthenticationUtil.authentication(actualName,idCard,bankNo,mobile);
                if(responseCode.equals("0000")){
                    BandBank bandBank = new BandBank();
                    bandBank.setId(UUIDGenerator.generate());
                    bandBank.setCreateTime(new Date());
                    bandBank.setActualName(actualName);
                    bandBank.setBankId(bank.getId());
                    bandBank.setBankNo(bankNo);
                    bandBank.setIdCard(idCard);
                    bandBank.setChannelId(channel.getId());
                    bandBank.setMobile(mobile);
                    bandBank.setUserId(user.getId());
                    bandBankDao.insertSelective(bandBank);
                }else{
                    logger.error(AuthenticationUtil.getResponseMsg(responseCode)+"，姓名["+actualName+"]身份证号["+idCard+"]银行卡号["+bankNo+"]手机号["+mobile+"]");
                    throw new BusinessException("10000",AuthenticationUtil.getResponseMsg(responseCode));
                }
                //7.绑卡
                BandBank bandBank = new BandBank();
                bandBank.setId(UUIDGenerator.generate());
                bandBank.setCreateTime(new Date());
                bandBank.setActualName(actualName);
                bandBank.setBankId(bank.getId());
                bandBank.setBankNo(bankNo);
                bandBank.setIdCard(idCard);
                bandBank.setChannelId(channel.getId());
                bandBank.setMobile(mobile);
                bandBank.setUserId(user.getId());
                bandBankDao.insertSelective(bandBank);
            }

        }catch(Exception e){
            if (e instanceof BusinessException) {
                code = ((BusinessException) e).getErrCode();
                errMsg = ((BusinessException) e).getErrMsg();
            }else{
                code = "10000";
                errMsg = "系统错误";
            }
            logger.error("用户绑定银行卡异常:"+errMsg );
            state = 0;
        }
        //8.返回数据
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        rtnMap.put("state", state);
        rtnMap.put("dataMap", dataMap);
        rtnMap.put("code",code);
        rtnMap.put("errMsg", errMsg);
        return rtnMap;
}