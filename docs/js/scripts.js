// src="https://www.gstatic.com/firebasejs/8.6.8/firebase-app.js"
// Your web app's Firebase configuration
var firebaseConfig = {
    apiKey: "AIzaSyBaAAZIojiCukUJfULuLqyR_4DHm8989rA",
    authDomain: "sistemmanajemenmenu.firebaseapp.com",
    databaseURL: "https://sistemmanajemenmenu-default-rtdb.firebaseio.com",
    projectId: "sistemmanajemenmenu",
    storageBucket: "sistemmanajemenmenu.appspot.com",
    messagingSenderId: "110856674391",
    appId: "1:110856674391:web:2d637de74414bae1da9712"
  };
  // Initialize Firebase
  if (!firebase.apps.length) {
    fb = firebase.initializeApp(firebaseConfig);
    firebase.analytics();
}
  

  var database = firebase.database();
  var storage = firebase.storage();

  //read 
  database.ref("/").on("value", (data)=>{
      const dta = data.val();
      console.info(dta);
  });

  const rupiah = (value) => {
    let keluaran = ""
    let keluaran2 = ""
    let counter = 0;
    for (var i = 0; i < value.length; i++) {
        counter++;
        if (counter == 3 && i < (value.length - 1)) {
            keluaran += value.charAt(value.length - i - 1)
            // keluaran += ""
            // counter = 0;
        } else {
            keluaran += value.charAt(value.length - i - 1)
        }
    }
    keluaran2 = reverseString(keluaran);
    return keluaran2;
}

const reverseString = (str) => {
    return str.split("").reverse().join("");
}


//kirim
const kirim = () => {
    let file = document.getElementById("file-image");
    // let date = new Date().getTime();
    file = file.files[0];
    let nameFile = `${file.name}`;
    let nam = document.getElementById("nama").value;
    let har = document.getElementById("harga").value;
    let kat = document.getElementById("kategori").value;
    let des = document.getElementById("deskripsi").value;

    var up = storage.ref("produk").child(nameFile).put(file);
    up.on('state_changed', snapshot => { }, error => { console.log(error) }, () => {
        up.snapshot.ref.getDownloadURL().then(downloadURL => {
            let uplo = {
                // urlImg: downloadURL,
                poster: nameFile,
                namamin: nam,
                harga: har,
                tag1: kat,
                tag2: des,
            }
            console.info(uplo);
            database.ref('/'+kat).set(uplo).then(()=>{
                M.toast({ html: 'Upload Berhasil', classes: 'blue' })
                window.open("/PPL4612SC/docs/index.html", "_self");
            })
        })
    })
        
}


//read
//assign this to a function, so this function only can be triggered at spesific page
const fetchData = () => {
    database.ref("/Food").on("value", (dtm) => {
        let nampil = dtm.val();
        let card = document.getElementById("card-menu");
        let dataht = "";
        
        for(key in nampil){
            let a = nampil[key].namamin;
            let b = rupiah(nampil[key].harga);
            let c = nampil[key].tag2;
            let f = nampil[key].iden;
            let d = nampil[key].urlImg;
            let e = nampil[key].poster;
            dataht += `<div class="col l4 s12 m6">
            <div class="card">
                <div class="card-image bluish">
                    <img src="${d}">
                    <span class="card-title pgn right-align">${b}</span>
                </div>
                <div class="card-content">
                    <span class="card-title">${a}</span>
                    <p>${c}</p>
                </div>
                <div class="card-action">
                    <a class="waves-effect waves-light modal-trigger" href="#modal1" onclick="edit(${key})">EDIT</a>
                    <a id="del" class="pinggir"  onclick="hapus(${key}, '${e}')">DELETE</a>
                </div>
            </div>
        </div>`;
            console.info(card);
        }
        card.innerHTML = dataht
    })
}

//delete
function hapus(key, nameFile) {

    database.ref("/Drink" + key).remove();
    storage.ref("produk").child(nameFile).delete();
}


//edit data
function edit(key) {
    database.ref("/Drink" + key).on("value", (datae) => {
        let nampl = datae.val();

        document.getElementById("nama").value = nampl.namamin;
        document.getElementById("harga").value = nampl.harga;
        document.getElementById("deskripsi").value = nampl.tag2;
        document.getElementById("kategori").value = nampl.tag1;
        //untuk menyimpan  key sementara
        document.getElementById("editNow").innerHTML = key;
        console.log(key);

    });
}

//trigger edit data
function tombolEdit() {
    let key = document.getElementById("editNow").innerHTML;
    let kat = document.getElementById("kategori").value;
    database.ref(`/Drink/${key}`).update({
        iden: key,
        namamin: document.getElementById("nama").value,
        harga: document.getElementById("harga").value,
        tag1: kat,
        tag2: document.getElementById("deskripsi").value
    }).then(document.getElementById("editNow").innerHTML = "");
}

//login
function login() {
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    firebase.auth().setPersistence(firebase.auth.Auth.Persistence.SESSION).then(() => {
        return firebase.auth().signInWithEmailAndPassword(email, password)
            .then(success => {
                console.log("Hey");
                window.open("/PPL4612SC/index.html", "_self");
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
function logout() {
    firebase.auth().signOut().then(success => {
        console.log("logout");
        window.open("/PPL4612SC/login.html", "_self");
    })
}

