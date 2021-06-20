// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
var firebaseConfig = {
    apiKey: "AIzaSyDMbejPbMli6gf3i5OUGq09C-HMY9NnMFI",
    authDomain: "apaan-dbc16.firebaseapp.com",
    databaseURL:"https://apaan-dbc16-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "apaan-dbc16",
    storageBucket: "apaan-dbc16.appspot.com",
    messagingSenderId: "412262375065",
    appId: "1:412262375065:web:af929785b8e1c873e5e208",
    measurementId: "G-CQ907QF5E6"
};

// Initialize Firebase
if(!firebase.apps.length){
fb = firebase.initializeApp(firebaseConfig);
firebase.analytics();
}

var database = firebase.database();
var storage = firebase.storage();

//read
// database.ref("/").on("value", (data)=>{
//     const dt = data.val();
//     console.log(dt);
// })


const rupiah = (value) => {
    let keluaran = ""
    let keluaran2 =""
    let counter = 0;
    for (var i =0; i< value.length; i++){
      counter++;
      if(counter == 3 && i < (value.length -1)){
        keluaran += value.charAt(value.length -i -1)
        keluaran += "."
        counter =0;
      }else{
        keluaran += value.charAt(value.length - i -1)
      }
    }
    keluaran2 = reverseString(keluaran);
    return keluaran2;
}

const reverseString = (str)=> {
    return str.split("").reverse().join("");
}


//write
// function kirim(){
// var date = new Date().getTime();
// var data = {iden: date,
// nama: document.getElementById("nama").value,
// harga: document.getElementById("harga").value,
// deskripsi: document.getElementById("deskripsi").value}
// database.ref(`/makanan/${date}`).set(data).then(console.log('kirimed'))
// }


const kirim = () => {
    let file = document.getElementById("file-image");
    var date = new Date().getTime();
    file = file.files[0];
    let nameFile = `${date}-${file.name}`;
    let kat = document.getElementById("kategori").value.split(',');

    var up = storage.ref("product").child(nameFile).put(file);
    up.on('state_changed', snapshot => {}, error => { console.log(error) }, () => {
        up.snapshot.ref.getDownloadURL().then(downloadURL => {
            let upl = {
                urlImg : downloadURL,
                docName: nameFile,
                iden: date,
                nama: document.getElementById("nama").value,
                harga: document.getElementById("harga").value,
                kategori: kat,
                deskripsi: document.getElementById("deskripsi").value
            }
            console.log(upl)
                database.ref(`/admin/menu/${date}`).set(upl).then(()=>{
                    M.toast({html: 'Upload Berhasil', classes:'blue'})
                    window.open("/PPL4612SC","_self");
                }) //admin/docs/index.html
        })
      
    })
  }


//read
//assign this to a function, so this function only can be triggered at spesific page
const fetchData = ()=>{
    database.ref("/admin/menu").on("value", (dtman)=>{
        let tampil = dtman.val();
        var card = document.getElementById("card-menu");
        let dataht = "";
        // var kunci = document.getElementById("identitas").value = dtman.val().iden;
        for(key in tampil){
            let a = tampil[key].nama;
            let b = rupiah(tampil[key].harga);
            let c = tampil[key].deskripsi;
            let d = tampil[key].iden;
            let e = tampil[key].urlImg;
            let f = tampil[key].docName;
            dataht += `<div class="col l4 s12 m6">
                        <div class="card">
                            <div class="card-image bluish">
                                <img src="${e}">
                                <span class="card-title pgn right-align">Rp. ${b}</span>
                            </div>
                            <div class="card-content">
                                <span class="card-title">${a}</span>
                                <p>${c}</p>
                            </div>
                            <div class="card-action">
                                <a class="waves-effect waves-light modal-trigger" href="#modal1" onclick="edit(${key})">EDIT</a>
                                <a id="del" class="pinggir"  onclick="hapus(${key}, '${f}')">DELETE</a>
                            </div>
                        </div>
                    </div>`;
            console.log(card);
        }
        card.innerHTML = dataht
  })        
}

//delete
function hapus(key, nameFile){

    database.ref("/admin/menu/"+key).remove();
    storage.ref("product").child(nameFile).delete();
}
// database.ref("/makanan").remove();

//edit data
function edit(key){
database.ref("/admin/menu/"+key).on("value", (datae)=>{
  let tampl = datae.val();
    
    document.getElementById("nama").value = tampl.nama;
    document.getElementById("harga").value = tampl.harga;
    document.getElementById("deskripsi").value = tampl.deskripsi;
    document.getElementById("kategori").value = tampl.kategori;
    //untuk menyimpan  key sementara
    document.getElementById("editNow").innerHTML = key;
    console.log(key);

});
}

//trigger edit data
function tombolEdit(){
let key = document.getElementById("editNow").innerHTML;
let kat = document.getElementById("kategori").value.split(',');
database.ref(`/admin/menu/${key}`).update({
    iden: key,
    nama: document.getElementById("nama").value,
    harga: document.getElementById("harga").value,
    kategori: kat,
    deskripsi: document.getElementById("deskripsi").value
}).then(document.getElementById("editNow").innerHTML = "");
}

//login
function login(){
let email = document.getElementById("email").value;
let password = document.getElementById("password").value;
firebase.auth().setPersistence(firebase.auth.Auth.Persistence.SESSION).then(() => {
    return firebase.auth().signInWithEmailAndPassword(email, password)
    .then(success => {
    console.log("Hey");
    window.open("/PPL4612SC/docs/index.html","_self");
    })
    .catch(error => {
    console.log("Gagal");
    window.alert("Email atau password yang dimasukkan salah!");
    })
}).catch(error => {
    console.log(error.message);
})


// firebase.auth().signInWithEmailAndPassword(email, password)
// .then((userCredential) => {
//   // Signed in
//   var user = userCredential.user;
//   // ...
// })
// .catch((error) => {
//   var errorCode = error.code;
//   var errorMessage = error.message;
// });
}

//logout
function logout(){
    firebase.auth().signOut().then(success =>{
        console.log("logout");
        window.open("/PPL4612SC/docs/login.html","_self");
    })
}

