import { Component, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MenuService } from '../menu.service';

@Component({
  selector: 'side-nav-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['../menu.component.css']
})
export class MenuItemComponent {
	@Input() menuItems;

	constructor(
		private router: Router, 
		private activatedRoute: ActivatedRoute,
		private menuService: MenuService
	){}

	onClicked(menuItem: any){
		this.router.navigate([menuItem.routerLink], {relativeTo: this.activatedRoute});
	}

	onToggleSubMenu(item) {
	    item.expanded = !item.expanded;
	}

	onSelectMenuItem(item) {
	    this.menuService.selectMenuItem(item);
	}
}