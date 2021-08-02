<template>
    <div>
        <Header></Header>
            
            <div class="container-pc">
			<div class="setting-container">
				<div   class="main-content">
					<div   class="left-content">
						<div   class="label-item label-active" id="btn_j"  @click="bang(1)">
							基本设置
						</div>
						<div   class="label-item" id="btn_b" @click="bang(2)">
							账号绑定
						</div>
						<div   class="label-item" id="btn_p" @click="bang(3)">
							修改密码
						</div>
					</div>
					<div   class="right-content">
						<div   class="label-one" id="label-one">
							<div   class="head-pic">
								头像
							</div>
							<div style='width:150px;'>
                                <el-upload
                                        class="avatar-uploader"
                                        action="1"
                                        list-type="picture-card"
                                        :on-preview="handlePictureCardPreview" 
                                        :on-remove="handleRemove"
                                        :http-request="myUpload"
                                        :class="{'demo-httpRequestImg':httpRequestImg}"
                                        >
                                    <i class="el-icon-plus"></i>
                                </el-upload>
                                <el-dialog :visible.sync="dialogVisible" append-to-body class="ImgClass">
                                    <img width="100%" :src="dialogImageUrl" alt="">
                                </el-dialog>
                            </div>
							<div   class="nick-name">
								昵称
							</div>
							<div   class="input-wrap"><input id="newNickName" type="text" maxlength="15" placeholder="请输入您的昵称" class="nick-input"> <em   class="error" style="display: none;"></em>
								<div   class="clear"></div>
							</div> 
                                <button @click="updateInfo" class="update-info">更新信息</button>
                            </div>
						<div   class="label-two" id="label-two" style="display: none;">
							<div   class="main-title">
								<div   class="title-left">
									绑定微信
								</div>
								<div   class="title-right">
									<a   href="javascript:;">解除绑定&gt;</a>
								</div>
							</div>
							<div   class="sub-title">
								当前已经绑定微信账号
							</div>
						</div>
						<div   class="label-three" id="label-three" style="display: none;">
							<div   class="main-title">
								<div   class="title-left">
									账户密码
								</div>
								<div   class="title-right" @click="dialogFormVisible = true">
									修改密码
								</div>
							</div>
							<div   class="sub-title">
								******
							</div>
						</div>
					</div>
				</div>
				<!---->
				<!-- Form -->
                <el-dialog title="修改密码" :visible.sync="dialogFormVisible">
                    <!-- <el-form :model="form"> -->
                        <el-form>
                        <el-form-item label="原密码" >
                        <el-input v-model="oldPwd" ></el-input>
                        </el-form-item>

                        <el-form-item label="新密码" >
                        <el-input v-model="newPwd1" ></el-input>
                        </el-form-item>

                        <el-form-item label="确认新密码">
                        <el-input v-model="newPwd2" ></el-input>
                        </el-form-item>
                    
                    </el-form>
                    <div class="dialog-footer">
                        <el-button class="confirm-button" type="primary" @click="updatePwd">确 定</el-button>
                        <el-button class="cancel-button" @click="dialogFormVisible = false">取 消</el-button>
                    </div>
                </el-dialog>
				<!---->
			</div>
		</div>

        <Footer></Footer>
  </div>
</template>

<script>
import Header from "./Header/Header"; //顶部登录条
import Footer from "./Footer/index"; //顶部登录条
import jwtDecode from 'jwt-decode'

export default {
    name: 'Setting',
    components: {
        Header,
        Footer
    },
    data() {
        return {
            dialogImageUrl: '',//预览url
            dialogVisibleImg:false,
            httpRequestImg:false,//展示单个图片
            dialogFormVisible: false, // 修改密码的模态框不显示
           
            dialogVisible:false,// 隐藏的图片文件框
            user: null,// 当前登录的用户对象信息
            fileId:"",// 上传图片后，返回的fastdfs的图片地址
            oldPwd:null,// 旧密码
            newPwd1:null,// 第一次新密码
            newPwd2:null,// 第二次新密码
            token:null,
            isLogin: false, // 登录状态，true：已登录，false：未登录
            
        };
    },
    created(){
        // 从url中获取token参数
        let token  = this.getValueByUrlParams('token');

        if(token == null || token == ""){
            // 从cookie中获取user的token
            token = this.getCookie("user");
        }

        this.token = token;
    
        //console.log("刷新setting页面token=>"+token);
        if(token != null || token != ""){
            // 将token发送到sso进行校验
            this.axios
            .get("http://localhost:8003/user/checkToken",{
                params:{
                    token:token
                }
            })
            .then( (result)=>{
                if(result.data.state == 4){
                    this.isLogin = true;
                    this.setCookie("user",token,600);
                    this.user = jwtDecode(token);
                    console.log("------>"+token);
                    console.log(this.user.userid);
                }
            })
            .catch( (error)=>{
            });
        }
        
    },
    methods: {
        redirectLogin() {
            window.open('/frontLogin.do', '_self');
        },
        //切换tab页
        bang(i){
            // alert( i );
            document.getElementById("btn_j").classList.remove("label-active");
            document.getElementById("btn_b").classList.remove("label-active");
            document.getElementById("btn_p").classList.remove("label-active");
            
            document.getElementById("label-one").style.display="none";
            document.getElementById("label-two").style.display="none";
            document.getElementById("label-three").style.display="none";

            if(i == 1){
                document.getElementById("btn_j").classList.add("label-active");
                document.getElementById("label-one").style.display="block";
            }else if(i == 2){
                document.getElementById("btn_b").classList.add("label-active");
                document.getElementById("label-two").style.display="block";
            }else{
                document.getElementById("btn_p").classList.add("label-active");
                document.getElementById("label-three").style.display="block";
            }
        },
        // 上传头像
        myUpload(content) {
            // 必须将表单中的数据进行封装才能发送，否则传不过去
            let form = new FormData();
            form.append("file",content.file);

            this.axios.post("http://localhost:8002/userSetting/upload" , form)
                .then( (result)=>{
                this.fileId = result.data.fileId;
                console.log("头像URL：" + this.fileId);
                // 正常情况下会出现第二个图片框，true为只显示一个
                this.httpRequestImg = true;
            } )
            .catch( (error)=>{
                this.$message.error("上传头像失败！");
            });
        },
        //更新个人信息
        updateInfo(){
            return this.axios.get("http://localhost:8002/userSetting/updateUser" , {
                params:{
                    userid:this.user.userid,
                    newName: document.getElementById("newNickName").value,
                    fileId:this.fileId
                }
            }).then( (result)=>{
               // 友好提示
               this.$message.success("上传头像失败！");
               // 退出
               this.logout();
               // 回到首页
               this.$router.push("/");
            } )
            .catch( (error)=>{
                this.$message.error("上传头像失败！");
            });
        },
        //修改密码
        updatePwd(){
           if(this.oldPwd == this.user.password){
               if(this.newPwd1 == this.newPwd2){
                    return this.axios.get("http://localhost:8002/userSetting/updatePassword" , {
                        params:{
                            userid:this.user.userid,
                            newPwd:this.newPwd2
                        }
                    }).then( (result)=>{
                        // 友好提示
                        this.$message.success("密码修改成功，请重新登录");
                        // 隐藏修改密码的对话框
                        this.dialogFormVisible = false;
                        // 退出
                        this.logout();
                        // 回到首页
                        this.$router.push("/");
                    } )
                    .catch( (error)=>{
                        this.$message.error("修改密码失败！");
                    });
               }else{
                    this.$message.error("两次密码不一致");
                    return;
               }
           }else{
               this.$message.error("原密码输入错误");
               return;
           }
        },        
        //预览
        handlePictureCardPreview(file) { 
            this.dialogImageUrl = file.url;
            this.dialogVisibleImg = true;
        },
        //删除
        handleRemove(file, fileList) { 
            this.httpRequestImg = false;
            console.log(file, fileList);
        },
        //登出
        logout(){ 
            //删除cookie
            this.delCookie("user"); 
            // 更新登录状态
            this.isLogin = false; 
            alert('谢谢使用，再见！');
            // 重定向到未登录的首页
            this.$router.push("/"); //回到首页
            // 去redis删除token
            this.axios
            .get("http://localhost:80/user/logout",{
                params:{
                token:this.token
                }
            })
            .then( (result)=>{
            })
            .catch( (error)=>{
                //this.$message.error("登录失败！");
            });
        },
        //设置cookie
        setCookie(key,value,expires){
            var exp = new Date();
            exp.setTime(exp.getTime() + expires*1000);
            document.cookie = key + "=" + escape (value) + ";expires=" + exp.toGMTString();
        },
        //从cookie中获取token
        getCookie(key){
            var name = key + "=";
            // if(document.cookie.indexOf(';') > 0){
            //     var ca = document.cookie.split(';');
            //     for(var i=0; i<ca.length; i++) {
            //         var c = ca[i].trim();
            //         if (c.indexOf(name)==0) { 
            //         return c.substring(name.length,c.length); 
            //         }
            //     }
            // }
            if(document.cookie.indexOf('.') > 0){
              var ca = document.cookie;
              var c = ca.trim();
              if (c.indexOf(name)==0) { 
                return c.substring(name.length,c.length); 
              }
            }
            return "";
        },
        //删除cookie
        delCookie(name){
            // 删除cookie只需要将值清空即可
            // -1 让指定名为name的cookie过期实现自动清除，如果不赋值-1的话，只是将对应的cookie值删除，而这条cookie记录并没有删除
            this.setCookie(name,'',-1);
        },
        // 获取url中参数
        getValueByUrlParams(paramKey) {
            // http://localhost:8080/#/?token=1&id=2
            var url = location.href;
            var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
            var paraObj = {}
            var i, j
            for (i = 0; j = paraString[i]; i++) {
                paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
            }
            var returnValue = paraObj[paramKey.toLowerCase()];
            if (typeof(returnValue) == "undefined") {
                return "";
            } else {
                return returnValue;
            }
        },

    },
    
};
</script>

<style lang="less" scoped>
    .demo-httpRequestImg{
      /deep/ .el-upload--picture-card{
          display: none;
      }
    }
    .login-banner-blank {
        height: 90px;
    }
    .login-banner {
       overflow: hidden;
       font-size: 0;
       height: 90px;
       position: fixed;
       width: 100%;
       top: 0;
       z-index: 4;
       background: #fff;
       -webkit-transform: translateZ(0);
       border-bottom: 0.5px solid #E8E9EB;
    }
    .bg {
        position: absolute;
        width: 120px;
        height: 100%;
        top: 0;
        z-index: -1;
    }
    .bg-left {
        left: 0;
        transform: rotate(180deg);
    }
    .bg-right {
        right: 0;
    }
    .edu-logo {
        float: left;
        width: 84px;
        height: 84px;
        margin-left: 36px;
    }
    .login-txt {
        font-size: 30px;
        color: #333333;
        margin-left: 16px;
        float: left;
        margin-top: 24px;
    }
    .login-btn {
        float: right;
        background: #E5F7F3;
        border-radius: 2px;
        width: 122px;
        font-size: 30px;
        color: #00B38A;
        text-align: center;
        font-weight: 400;
        line-height: 30px;
        padding: 15px 0;
        margin-right: 40px;
        margin-top: 15px;
        border: none;
        outline: none;
        &:active {
            background: none;
        }
    }
* {
    margin: 0;
    padding: 0;
}
div {
    display: block;
}

    .container-pc {
    min-height: calc(100vh - 300px);
    background: #f8f9fa;
    content: "viewport-units-buggyfill; min-height: calc(100vh - 300px)";
}
.setting-container{
    width: 1024px;
    box-sizing: border-box;
    margin: 17px auto 0;
    padding: 0 20px;
}
.setting-container .main-content{
    margin: 66px auto 0;
    height: 400px;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
}
.setting-container .main-content .left-content {
    width: 234px;
    height: 100%;
    border-right: 1px solid #eee;
}
.setting-container .main-content .left-content .label-active{
    border-right: 2px solid #e5a817;
    color: #e5a817;
    -webkit-transform: translateX(1.5px);
    transform: translateX(1.5px);
}
.setting-container .main-content .left-content .label-item {
    height: 39px;
    line-height: 39px;
    text-align: right;
    padding-right: 62px;
    font-size: 14px;
    font-family: PingFangSC-Regular,PingFang SC;
    font-weight: 400;
    color: #555;
    cursor: pointer;
}
.setting-container .main-content .right-content {
    width: 580px;
    height: 100%;
    margin-left: 67px;
}

.setting-container .main-content .right-content .label-one .head-pic {
    text-align: left;
    height: 39px;
    font-size: 14px;
    font-family: HiraginoSansGB-W3,HiraginoSansGB;
    font-weight: 400;
    color: #555;
    line-height: 39px;
}
.setting-container .main-content .right-content .label-one .pic-wrap {
    margin: 8px 8px 20px;
    width: 80px;
    height: 80px;
    line-height: 80px;
    overflow: hidden;
    border-radius: 50%;
    text-align: center;
    font-size: 14px;
    font-family: HiraginoSansGB-W3,HiraginoSansGB;
    font-weight: 400;
    color: #555;
}
.setting-container .upload-btn{
    width: 98px;
    height: 32px;
    cursor: pointer;
    box-sizing: border-box;
    text-align: right;
    padding: 0 9px;
    line-height: 30px;
    border-radius: 3px;
    border: 1px solid #d9d9d9;
    font-size: 14px;
    font-family: HiraginoSansGB-W3,HiraginoSansGB;
    font-weight: 400;
    color: #555;
    position: relative;
}
.setting-container .upload-btn:before {
    content: "";
    position: absolute;
    width: 15px;
    height: 15px;
    left: 9px;
    top: 6px;
    cursor: pointer;
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAMAAAAM7l6QAAAARVBMVEUAAACZmZmZmZmdnZ2ZmZmZmZmZmZmYmJiZmZmXl5eYmJiYmJiYmJiZmZmXl5eYmJiXl5eYmJiYmJiYmJiYmJiXl5eXl5dKwt9uAAAAFnRSTlMAFBkaHi03RVBRWV53eIe65ebr8Pr9chaB0QAAAH1JREFUKM/Vz8sWgyAMRdFoH9QqxT68//+pIhJcQMKsA88s2ZOEKM++qJEFGj4BDZ+wAD/NvY7AoLj1SgDJHnRj0XcNLHjUnStnjVx4UubMuw9rYu/fK/vtSSXT4y48f7DYqdg5jYNkm3rINsY0uT7kz+yUAr+hNnvujdplBd8MD9nFJBOFAAAAAElFTkSuQmCC) no-repeat;
    background-size: 100% 100%;
}
.setting-container .upload-btn .av_upload{
    opacity: 0;
    cursor: pointer;
    left: -.04rem;
    top: -.027rem;
    width: 98px;
    height: 32px;
    position: relative;
    z-index: 10;
}
.nick-name {
    padding: 0 12px;
    width: 292px;
}
input {
    font-family: Microsoft Yahei,SimSun,Arial,Helvetica Neue,Helvetica;
}
.nick-input {
    -webkit-appearance: none;
    background-color: #fff;
    background-image: none;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    box-sizing: border-box;
    color: #606266;
    display: inline-block;
    font-size: inherit;
    height: 40px;
    line-height: 40px;
    outline: 0;
    padding: 0 15px;
    -webkit-transition: border-color .2s cubic-bezier(.645,.045,.355,1);
    transition: border-color .2s cubic-bezier(.645,.045,.355,1);
    width: 100%;
}
.setting-container .upload-btn .change-text {
    cursor: pointer;
    position: absolute;
    top: -1px;
    left: 31px;
    z-index: 1;
}
.setting-container .main-content .right-content .label-one .nick-name {
    text-align: left;
    margin: 60px 0 20px;
    height: 14px;
    font-size: 14px;
    font-family: HiraginoSansGB-W3,HiraginoSansGB;
    font-weight: 400;
    color: #555;
    line-height: 14px;
}
.setting-container .main-content .right-content .label-one .input-wrap{
    position: relative;
    width: 292px;
    height: 40px;
}
.setting-container .main-content .right-content .label-one .update-info{
    display: block;
    margin-top: 40px;
    width: 104px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    background: #fcd766;
    border-radius: 3px;
    font-size: 16px;
    font-family: HiraginoSansGB-W3,HiraginoSansGB;
    font-weight: 400;
    color: #333;
}
a, button, input {
    outline: 0 none;
}
button, input[type=button], input[type=checkbox], input[type=file], input[type=radio], input[type=reset], input[type=submit], label, select {
    cursor: pointer;
}
button, input, optgroup, select, textarea {
    margin: 0;
    padding: 0;
    border: 1px solid #ededed;
    border-radius: 0;
    font-family: Hiragino Sans GB,Microsoft Yahei,SimSun,Arial,Helvetica Neue,Helvetica;
}
.setting-container .main-content .right-content .label-two{
    margin-top: 12px;
    width: 71%;
    height: 56px;
    box-sizing: border-box;
    padding-left: 76px;
    position: relative;
}
.setting-container .main-content .right-content .label-two .main-title{
    height: 30px;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    justify-content: space-between;
}
.setting-container .main-content .right-content .label-two .main-title .title-left {
    height: 30px;
    font-size: 18px;
    font-family: PingFangSC-Semibold,PingFang SC;
    font-weight: 600;
    color: #333;
    line-height: 30px;
}
.setting-container .main-content .right-content .label-two .main-title .title-right{
    height: 30px;
    font-size: 14px;
    font-family: PingFangSC-Regular,PingFang SC;
    font-weight: 400;
    color: #5580c8;
    line-height: 30px;
    cursor: pointer;
}
.setting-container .main-content .right-content .label-two .sub-title {
    height: 26px;
    font-size: 14px;
    font-family: HiraginoSansGB-W3,HiraginoSansGB;
    font-weight: 400;
    color: #555;
    line-height: 26px;
}

.setting-container .main-content .right-content .label-two:before {
    content: "";
    width: 56px;
    height: 56px;
    position: absolute;
    top: 0;
    left: 0;
    background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHAAAABwCAMAAADxPgR5AAAAh1BMVEVHcExTvjNVwTZn1kVSvjNSvjNTvjRSvjNYxj5SvjNTvjS//79SvjRZwTdSvjNUvjVTvjRcxjlSvjNZxTZUvzRTvjNVwDZTwDRTvjRSvjNTvjNUvzVaxThSvjNTvzRTvjNSvjNTvjNWvzZSvjNTvzNTvjNTvjRTvjNUvzRTvjNSvjRSvTNSvTMqRat8AAAALHRSTlMAqSoE7fO//Ar42AGYHJ5Cdw63GFSwJU1czX80E9JsaMXeIeVxjD2SR6KH1skvj5YAAAQYSURBVGje7ZvZtqowDIYLtpRZFEFAcEIc8/7Pdy6cyiRBoRdn7dwJLD6oSfO3CYS8TV/amslgUKOmZi910mSqMjDrbUxRazjH5TCicdcp86YURjY6FXlrDqMbXws8kGIv4pTLAfLHqDoUJBm9e44L0swlhBCVywNylRCigERTCNGZTCDTyRKk2pLYcoE20eQCNWLKBZqEyQVSApLtD/gH/O+AXBaQzb3FytctKw1Xy41rjgvcJsdJRcamyxMdCWjYYaNQJ3qkjQCkV5+0W6gMDXRn5LMdsyGBdEE6bbJhgwHnPsGYuhsIeCJIS7VBgLaFBZKJOwDQxfMIsYKfgUofHiF6/CNwV1qVW8doWplq9ONiL/7285+AfFVywxsA7EqHFhQAlFRcQv8EtBsengpvdHzEjXhZ8AOQlQIwqYfJ9nFIXLbvje+B5QicP47G7zB43tsTr7t+DyynB+Ul0l9O9JzNDqWNl6+B27JDPncckvpLlxzpNdC9gV5lHslqkaIa7wX02w7fAlfVuVLhAEHJkVYZgJGIsan7+v5LIJ/UJ5JVLS/66vOy/UbZ7hhaYNWBtz5z2qq4AYARzy+Fd7i689joD9R64AIAHl/Pwt+rn4usJzDA4tQAgBVO/cTeNvoAFSQvYpB7LZrHv1I8cI5LujbA5YPE8gM0MEbJirhTYrWoqzowxyTcLcRO10XhDRn4e4ykyNLux/JNHLBbjZ7AREnIJmID8NJ1nynkDs6TnRwDZHqHg2biIMxqrpoKAuyMyodRh/sJc0O4BdBK+XNpAr2+kRcMcDf5xJtR9hrQ2a6auc4GAEDxft0cI6I2H2cY4XZRzc3ust+YtWbJRiD95IMBqFWBJaRssxpaqYERwh+mt5mRCX/Y/fJzVQHRSat+bJH63ocRFc5ZWvXxwhwAuBjKS9xipjX67ZIE0ZM4LkkNEtqZci5NgxwFNKYtQI3rpJ/dcOtDo+Ud87wn7yUqu1bAvDk4qNkXeEFvKrhNgyc66dBAyBqWpXS0Ia2L/ntkM6snMMMDD01eCmE/3ozjgU1Zr+hKJlU743eiHiOqe9opein7BVLWtSUo0jWiuneXmEasFN56czgB9/vwUoYHOm9cydZ9gAl+r23bjAMwJ3iew/DAxGvT6wkeGPTYTWxfkRjoyNgMVEbQkIO6YkPVLXB7fyEdrlBif7t1+3Vlpuh8x+YOlu9LQV071JExdO3p4wLRisYodjV0HlU18tDVteCIzErDlfPiSmFKn3p7QfSPUj9kQbJZHNW9Ot3YMQcwirR9622cgiU9TEgot0K6iyxTckk23v4Vnf9XoOT2Fia/gUd6i5ItF2jLbzOT3kgnvVVQfjOk9HZP+Q2t0lt25Tcly2+7lt9YLr91Xv7HAWN9/sCqnz/8Az52G/EKSOy2AAAAAElFTkSuQmCC) no-repeat;
    background-size: 100% 100%;
}

.setting-container .main-content .right-content .label-three {
    margin-top: 12px;
    width: 71%;
    height: 56px;
    box-sizing: border-box;
}
.setting-container .main-content .right-content .label-three .main-title{
    height: 30px;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    justify-content: space-between;
}

.password-change-modal-wrap .el-dialog__footer {
    padding: 0 50px 50px;
}
.el-dialog__footer {
    padding: 10px 20px 20px;
    text-align: right;
    box-sizing: border-box;
}
 .dialog-footer {
    display: block;
    font-size: 0;
    text-align: left;
}
 .confirm-button{
    background: #00b38a;
    color: #fff;
}

 .confirm-button{
    display: inline-block;
    vertical-align: top;
    text-align: center;
    padding: 12px 22px;
    border-radius: 3px;
    font-size: 16px;
    line-height: 16px;
    cursor: pointer;
}
.cancel-button{
    color: #00b38a;
    margin-left: .053rem;
    display: inline-block;
    vertical-align: top;
    text-align: center;
    padding: 12px 22px;
    border-radius: 3px;
    font-size: 16px;
    line-height: 16px;
    cursor: pointer;
}
.setting-container .main-content .right-content .label-three .main-title .title-left{
    height: 30px;
    font-size: 18px;
    font-family: PingFangSC-Semibold,PingFang SC;
    font-weight: 600;
    color: #333;
    line-height: 30px;
}
.setting-container .main-content .right-content .label-three .main-title .title-right{
    height: 30px;
    font-size: 14px;
    font-family: PingFangSC-Regular,PingFang SC;
    font-weight: 400;
    color: #5580c8;
    line-height: 30px;
    cursor: pointer;
}
.setting-container .main-content .right-content .label-three .sub-title {
    height: 26px;
    font-size: 14px;
    font-family: HiraginoSansGB-W3,HiraginoSansGB;
    font-weight: 400;
    color: #555;
    line-height: 26px;
}
.el-dialog__wrapper {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    overflow: auto;
    margin: 0;
}
element.style {
    margin-top: 25vh;
    width: 500px;
}
.el-dialog--center {
    text-align: center;
}
.el-dialog {
    position: relative;
    margin: 0 auto 50px;
    background: #fff;
    border-radius: 2px;
    box-shadow: 0 1px 3px rgba(0,0,0,.3);
    box-sizing: border-box;
    width: 50%;
}
.el-dialog__header {
    padding: 20px 20px 10px;
}
.el-dialog__title {
    line-height: 24px;
    font-size: 18px;
    color: #303133;
}

.el-icon-close:before {
    content: "\E6DB";
}
* {
    -webkit-tap-highlight-color: transparent;
}
* {
    margin: 0;
    padding: 0;
}
.el-dialog__headerbtn {
    position: absolute;
    top: 20px;
    right: 20px;
    padding: 0;
    background: 0 0;
    border: none;
    outline: 0;
    cursor: pointer;
    font-size: 16px;
}
.el-dialog__headerbtn .el-dialog__close {
    color: #909399;
}
[class*=" el-icon-"], [class^=el-icon-] {
    font-family: element-icons!important;
    speak: none;
    font-style: normal;
    font-weight: 400;
    -webkit-font-feature-settings: normal;
    font-feature-settings: normal;
    font-variant: normal;
    text-transform: none;
    line-height: 1;
    vertical-align: initial;
    display: inline-block;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
em, i {
    font-style: normal;
}
</style>