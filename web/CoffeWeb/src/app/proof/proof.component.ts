import { Component, Input, OnInit } from '@angular/core';

interface PreguntasInterface {
  name: string;
  lastname: String;
  adress: number;
}


@Component({
  selector: 'app-proof',
  templateUrl: './proof.component.html',
  styleUrls: ['./proof.component.css']
})

export class ProofComponent implements OnInit {
  texto: string =  "SI";
  estadoPositivo: boolean = true;
  lado: String = " h ";

  procesarEvento($event) {
    this.poligono.nombre = "perro";
  }
  contactos = [1,2,3];
  
  seleccionaContacto(contacto) {
    console.log('Se ha hecho clic sobre el contacto', contacto);
  }


  procesaClic( p) {
    p.style.color = 'red';
  }
  
  // cliente = {
  //   nombre ="pedro",
  //   cif="jojo",
  //   direccion="cliecion"
  // }

  preguntas: string[] = [
    "¿España ganará la Euro 2016?",
    "¿Hará sol el día de mi boda?",
    "¿Estás aprendiendo Angular 2 en DesarrolloWeb?",
    "¿Has hecho ya algún curso en EscuelaIT?"
  ];

  preguntasObj: PreguntasInterface[] = [
    {
      name: "¿España ganará la Euro 2016?",
      lastname:"¿España ganará la Euro 2016?",
      adress: 95
    },
    {
      name: "¿Estás aprendiendo Angular 2 en DesarrolloWeb??",
      lastname: "¿Estás aprendiendo Angular 2 en DesarrolloWeb??",
      adress: 3
    },
    {
      name: "¿Has hecho ya algún curso en EscuelaIT??",
      lastname: "¿Has hecho ya algún curso en EscuelaIT??",
      adress: 1
    }
  ]

  constructor() { }

  ngOnInit() {
    console.log('componente inicializado...');
  }


  cambiaEstado() {
    this.texto = (this.estadoPositivo) ?  "NO" : "SI";
    this.estadoPositivo = !this.estadoPositivo;
  }

  cambiaLado(valor) {
    this.lado = valor;
  }

  boton = false;
  estadoBoton(){
    this.boton = true;
  }

  hh = "https://www.google.co.ve/imgres?imgurl=http%3A%2F%2Fwww.conejos.wiki%2FImagenes%2Fimagen-de-un-conejo-enano.jpg&imgrefurl=http%3A%2F%2Fwww.conejos.wiki%2Fimagenes-imagen-de-un-conejo-enano-jpg&docid=nBic1SyYTfpgrM&tbnid=hshKu8YAaFFjIM%3A&vet=10ahUKEwjnycK-0OrbAhWI61MKHWlYCEAQMwg-KAswCw..i&w=1024&h=768&bih=1132&biw=2160&q=imagen&ved=0ahUKEwjnycK-0OrbAhWI61MKHWlYCEAQMwg-KAswCw&iact=mrc&uact=8";

  poligono = {
    nombre: 'cuadrado',
    puntos: [
      {
        x: 10,
        y: 5
      },
      {
        x: 15,
        y: 5
      },
      {
        x: 15,
        y: 10
      },
      {
        x: 10,
        y: 10
      }
    ],
    figuras: [
      {
        x: 10,
        y: 5
      },
      {
        x: 15,
        y: 5
      }
    ]
  }



}
