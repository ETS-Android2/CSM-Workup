//    This file is part of WorkUp.
//
//    WorkUp is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    WorkUp is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with WorkUp.  If not, see <http://www.gnu.org/licenses/>.
package br.com.adapters;

import br.com.GUI.avaliacoes.AvaliarGorduraCorporal;
import br.com.GUI.avaliacoes.AvaliarPerimetria;
import br.com.GUI.avaliacoes.ConcluirAvaliacao;
import br.com.GUI.avaliacoes.QuestionarioQPAF;
import br.com.GUI.avaliacoes.SituacaoCoronaria;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapterAvaliacoes extends FragmentPagerAdapter {

	public TabsPagerAdapterAvaliacoes(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
		    return new SituacaoCoronaria();
	
		case 1:
			return new QuestionarioQPAF();
			
		case 2:
			return new AvaliarGorduraCorporal();
		
		case 3:
			return new AvaliarPerimetria();
			
		case 4: 
			return new ConcluirAvaliacao();
			
		default:
			return null;
		}
	

	}
	

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}
