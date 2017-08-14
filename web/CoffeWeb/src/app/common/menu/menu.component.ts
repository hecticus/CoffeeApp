import { Component } from '@angular/core';
import { MenuService } from './menu.service';
import { RouterLink } from '@angular/router';

interface menuItem {
	title: string;
	routerLink?: string; 	
	selected?: boolean;
	expanded?: boolean;
	children?: menuItem[];
}

@Component({
	selector: 'side-nav',
	templateUrl: './menu.component.html',
	styleUrls: ['./menu.component.css'],
	providers: [MenuService],
})
export class MenuComponent {
	menuItems: menuItem[] = [
		{
			title: 'Proveedores',
			routerLink: './xxx/yyy',
			selected: false
		},{
			title: 'Lotes',
			routerLink: './xxx',
			selected: false
		},{
			title: 'Usuarios',
			expanded: false,
			children: [
				{	
					title: 'Listar',
					routerLink: './xxx',
					selected: false
				},{	
					title: 'Crear',
					routerLink: './xxx',
					selected: false
				}
			]
		},{	
			title: 'salir',
			routerLink: './user/logout',
			selected: false
		}
	];
}